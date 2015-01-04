package edu.agh.sp.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("")
public class ConnectService {

	@POST
	@Path("/register")
	@Produces(MediaType.TEXT_PLAIN)
	public Response registerDevice(byte[] gzippedWsdl) {
		try {
			String wsdl = decompress(gzippedWsdl);
			System.out.println(wsdl);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok().build();
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
}