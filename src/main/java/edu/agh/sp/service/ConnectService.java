package edu.agh.sp.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import edu.agh.sp.ejb.ServersHolderBean;
import edu.agh.sp.model.ASServerObject;

@Path("")
@Stateless
public class ConnectService {
	private static final Logger logger = Logger.getLogger(ConnectService.class.getName());

	@EJB
	private ServersHolderBean holder;

	@POST
	@Path("/register")
	@Produces(MediaType.TEXT_PLAIN)
	public Response registerDevice(byte[] gzippedWsdl, @HeaderParam("deviceIp") String ip, @HeaderParam("devicePort") String port) {
		try {
			if (gzippedWsdl == null || StringUtils.isEmpty(ip) || StringUtils.isEmpty(port)) {
				return Response.status(Status.BAD_REQUEST).build();
			}
			String wsdl = decompress(gzippedWsdl);
			wsdl = updateWsdlLocationTag(wsdl, ip, port);
			String serverDeviceId = UUID.randomUUID().toString();
			ASServerObject soapServerObject = new ASServerObject(serverDeviceId, new DateTime(), ip, Integer.parseInt(port), wsdl);
			holder.addServer(soapServerObject);
			logger.info("Mobile server registered. IP: " + ip + ", port: " + port);
			return Response.ok().header("deviceToken", soapServerObject.getServerDeviceId()).build();
		} catch (Exception e) {
			logger.severe("Exception occured: " + e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("/deregister")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deregisterDevice(@HeaderParam("deviceToken") String token) {
		if (StringUtils.isEmpty(token)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		holder.removeServer(token);
		logger.info("Mobile server with token: " + token + " deregistered.");
		return Response.ok().build();
	}

	@POST
	@Path("/ping")
	@Produces(MediaType.TEXT_PLAIN)
	public Response ping(@HeaderParam("deviceToken") String token) {
		if (StringUtils.isEmpty(token)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		holder.updatePingTimeForServer(token, new DateTime());
		logger.fine("Ping received from: " + token);
		return Response.ok().build();
	}

	@GET
	@Path("/wsdl/{token}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getWsdlForToken(@PathParam("token") String token) {
		if (StringUtils.isEmpty(token)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		ASServerObject asServerObject = holder.getServerForToken(token);
		if (asServerObject != null) {
			return Response.ok(asServerObject.getServerWsdlCopy()).build();
		} else {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	private static String decompress(byte[] compressed) throws IOException {
		final int BUFFER_SIZE = 1024;
		ByteArrayInputStream is = new ByteArrayInputStream(compressed);
		GZIPInputStream gis = new GZIPInputStream(is, BUFFER_SIZE);
		StringBuilder string = new StringBuilder();
		byte[] data = new byte[BUFFER_SIZE];
		int bytesRead;
		while ((bytesRead = gis.read(data)) != -1) {
			string.append(new String(data, 0, bytesRead));
		}
		gis.close();
		is.close();
		return string.toString();
	}

	private static String updateWsdlLocationTag(String wsdl, String ip, String port) {
		return StringUtils.replaceOnce(wsdl, 
				"<SOAP:address location=\"http://localhost:8080\"/>", 
				"<SOAP:address location=\"http://" + ip + ":" + port + "\"/>");
	}
}