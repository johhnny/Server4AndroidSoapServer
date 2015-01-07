package edu.agh.sp.service;

import edu.agh.sp.ejb.ServersHolderBean;
import edu.agh.sp.model.ASServerObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.zip.GZIPInputStream;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("")
@Stateless
public class ConnectService {
    @EJB
    private ServersHolderBean holder;

    @POST
    @Path("/register")
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerDevice(byte[] gzippedWsdl, @HeaderParam("deviceIp") String ip, @HeaderParam("devicePort") String port) {
        System.err.println("Polaczenie " + ip + ":" + port);

        ASServerObject soapServerObject;
        try {
            String wsdl = decompress(gzippedWsdl);
            soapServerObject = createNewServer(wsdl, ip, port);
            holder.addServer(soapServerObject);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().header("deviceToken", soapServerObject != null ? soapServerObject.getServerDeviceId() : "").build();
    }

    @POST
    @Path("/deregister")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deregisterDevice(@HeaderParam("deviceToken") String token) {
        System.out.println("Deregister: " + token);
        return Response.ok().build();
    }

    @POST
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public Response ping(@HeaderParam("deviceToken") String token) {
        System.out.println("Ping: " + token);
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

    // TODO
    private static ASServerObject createNewServer(String wsdl, String ip, String port) {
        ASServerObject newObject = new ASServerObject();
        newObject.setServerIpAddress(ip);
        newObject.setServerPort(port);
        newObject.setServerWsdlCopy(wsdl);
        newObject.setServerDeviceName("Android device " + ip + ":" + port);

        return newObject;
    }
}