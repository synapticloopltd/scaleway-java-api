package synapticloop.scaleway.api;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import synapticloop.scaleway.api.exception.ScalewayApiException;
import synapticloop.scaleway.api.model.Image;
import synapticloop.scaleway.api.model.Server;
import synapticloop.scaleway.api.model.ServerAction;
import synapticloop.scaleway.api.model.ServerTask;
import synapticloop.scaleway.api.model.ServerTaskStatus;
import synapticloop.scaleway.api.model.ServerType;
import synapticloop.scaleway.api.model.Volume;

public class Main {
	private static String getUbuntuImage(ScalewayApiClient scalewayApiClient) throws ScalewayApiException {
		for (int i = 1; i < Integer.MAX_VALUE; i++) {
			List<Image> images = scalewayApiClient.getAllImages(i, 100).getImages();
			for (Image image : images) {
				if("Ubuntu Xenial (16.04 latest)".equals(image.getName())) {
					return(image.getId());
				}
			}
		}

		return(null);
	}

	private static String getOrganizationId(ScalewayApiClient scalewayApiClient) throws ScalewayApiException {
		return(scalewayApiClient.getAllOrganizations().get(0).getId());
	}


	public static void main(String[] args) throws ScalewayApiException {
		// you can spin up a VM in either Amsterdam or Paris
		ScalewayApiClient scalewayApiClient = new ScalewayApiClient(System.getenv("YOUR_SCALEWAY_API_TOKEN_GOES_HERE"), Region.PARIS1);

		// a simple creation of a server
		Server server = scalewayApiClient.createServer("This is a test server", 
				getUbuntuImage(scalewayApiClient), 
				getOrganizationId(scalewayApiClient), 
				ServerType.VC1S, 
				new String[] {"lots", "of", "tags"});

		// now that we have created the server (and a volume is also created for it)
		// we need to power it on this may take some time - so we need to wait until
		// it is finished
		ServerTask powerOnServerTask = scalewayApiClient.executeServerAction(server.getId(), ServerAction.POWERON);
		boolean isStarted = false;
		while(!isStarted) {
			ServerTask taskStatus = scalewayApiClient.getTaskStatus(powerOnServerTask.getId());
			System.out.println(String.format("Server task with id '%s' is in current state '%s' (progress '%s')", taskStatus.getId(), taskStatus.getStatus(), taskStatus.getProgress()));
			try {
				Thread.sleep(10000);
			} catch (InterruptedException ex) {
				System.err.println("The sleeping thread was interrupted, continuing...");
			}
			if(taskStatus.getStatus() == ServerTaskStatus.SUCCESS) {
				isStarted = true;
			}
		}

		// now we can power down the server
		ServerTask powerOffServerTask = scalewayApiClient.executeServerAction(server.getId(), ServerAction.POWEROFF);
		boolean isEnded = false;
		while(!isEnded) {
			ServerTask taskStatus = scalewayApiClient.getTaskStatus(powerOffServerTask.getId());
			System.out.println(String.format("Server task with id '%s' is in current state '%s' (progress '%s')", taskStatus.getId(), taskStatus.getStatus(), taskStatus.getProgress()));
			try {
				Thread.sleep(10000);
			} catch (InterruptedException ex) {
				System.err.println("The sleeping thread was interrupted, continuing...");
			}

			if(taskStatus.getStatus() == ServerTaskStatus.SUCCESS) {
				isEnded = true;
			}
		}

		// now delete the server
		scalewayApiClient.deleteServer(server.getId());

		// don't forget to delete any attached volumes
		Map<String, Volume> volumes = server.getVolumes();
		Iterator<String> iterator = volumes.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Volume volume = volumes.get(key);
			scalewayApiClient.deleteVolume(volume.getId());
		}
	}

}
