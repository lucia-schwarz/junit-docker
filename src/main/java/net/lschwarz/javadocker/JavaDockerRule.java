package net.lschwarz.javadocker;

import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DockerClientBuilder;

public class JavaDockerRule extends ExternalResource {

	private DockerClient dockerClient;
	private String imageName;
	private CreateContainerResponse container;

	public JavaDockerRule(String imageName) {
		this.imageName = imageName;
		dockerClient = DockerClientBuilder.getInstance("unix:///var/run/docker.sock").build();
	}

	@Override
	protected void before() throws Throwable {

		ExposedPort tcp8080 = ExposedPort.tcp(8080);
		ExposedPort tcp9990 = ExposedPort.tcp(9990);

		Ports portBindings = new Ports();
		portBindings.bind(tcp8080, Ports.Binding.bindPort(80));
		portBindings.bind(tcp9990, Ports.Binding.bindPort(9990));
		container = dockerClient.createContainerCmd(imageName).withExposedPorts(tcp8080, tcp9990)
				.withPortBindings(portBindings).exec();

		System.out.println("Starting container...");
		dockerClient.startContainerCmd(container.getId()).exec();

	}

	@Override
	protected void after() {
		System.out.println("Stopping container...");
		dockerClient.stopContainerCmd(container.getId()).exec();
	}

}
