/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.fimnet.omi; // webapps/FimnetOMI/WEB-INF/class/vn/fimnet/omi/config.txt

import com.verimatrix.schemas.omitypes.Device;
import com.verimatrix.schemas.omitypes.Package;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import vn.fimnet.JSONFormat;

import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import vn.fimnet.OMIClient;
/**
 * REST Web Service
 *
 * @author tunghx
 */
@Path("api")
public class FimnetAPI extends OMIClient {

	@Context
	private UriInfo context;
	
	private static Properties config;
	private static String sessionKey = "";
	private String responseBody = null;
	/**
	 * Creates a new instance of FimnetAPI
	 * @throws java.lang.Exception
	 */
	public FimnetAPI() throws Exception {
        	setFormatter(new JSONFormat());
		if(config == null) {
			config = new Properties();
			InputStream inStream = new FileInputStream("/opt/fimnet/config.cfg");
			config.load(inStream);
		}
	}

	@POST
	@Path("signOn")
        @Produces("application/json")
	public String signOn() {
		try {
			this.admSignOn("admin", "newpass123");
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	
	@POST
	@Path("signOff")
        @Produces("application/json")
	public String signOff() {
		try {
			this.admSignOff();
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	
	@POST
        @Produces("application/json")
	public String show() {
		return "{Success: true, session:" + sessionKey + "}";
	}
	@POST
	@Path("listNetworks")
        @Produces("application/json")
	public String listNetworks() {
		try {
			this.confListNetworks();
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}

	@POST
	@Path("matchDevices/{networkId}/{count}/{devIdPrefix}")
        @Produces("application/json")
	public String matchDevices(@PathParam("networkId") String networkId, 
				  @PathParam("count") int count,
				  @PathParam("devIdPrefix") String devIdPrefix) {
		try {
			if (devIdPrefix == null) devIdPrefix = "";
			this.matchNDevices(networkId, devIdPrefix, devIdPrefix, count);
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	@POST
	@Path("matchDevices/{networkId}/{count}/{devIdPrefix}/{startId}")
        @Produces("application/json")
	public String matchDevices(@PathParam("networkId") String networkId, 
				  @PathParam("count") int count,
				  @PathParam("devIdPrefix") String devIdPrefix,
				  @PathParam("startId") String startId) {
		try {
			if (devIdPrefix == null) devIdPrefix = "";
			this.matchNDevices(networkId, devIdPrefix, startId, count);
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	@POST
	@Path("matchDevices/{networkId}/{count}")
        @Produces("application/json")
	public String matchDevices(@PathParam("networkId") String networkId, 
				  @PathParam("count") int count) {
		try {
			this.matchNDevices(networkId, "", "", count);
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	@POST
	@Path("listDevices/{networkId}/{count}/{lastDevId}")
        @Produces("application/json")
	public String listDevices(@PathParam("networkId") String networkId, 
				  @PathParam("count") int count,
				  @PathParam("lastDevId") String lastDevId) {
		try {
			if (lastDevId == null) lastDevId = "";
			this.listNDevices(networkId, lastDevId, count);
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	@POST
	@Path("listDevices/{networkId}")
        @Produces("application/json")
	public String listDevices(@PathParam("networkId") String networkId) {
		try {
			this.listNDevices(networkId, "", 10);
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}

	@POST
	@Path("listPackages")
        @Produces("application/json")
	public String listPackages() {
		try {
			this.entListPackages("", 10);
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	@POST
	@Path("listPackages/{lastPkgId}")
        @Produces("application/json")
	public String listPackages(@PathParam("lastPkgId") String lastPkgId) {
		try {
			this.entListPackages(lastPkgId, 10);
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}

	@POST
	@Path("listContent")
        @Produces("application/json")
	public String listContent() {
		try {
			this.contListContent("", 10);
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	@POST
	@Path("listContent/{contentId}/{count}")
        @Produces("application/json")
	public String listContent(@PathParam("contentId") String contentId,
				  @PathParam("count") int count) {
		try {
			if (count <= 0) count = 10;
			this.contListContent(contentId, count);
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	@POST
	@Path("matchContent/{count}/{contentIdPrefix}/{startId}")
        @Produces("application/json")
	public String matchContent(@PathParam("count") int count,
				  @PathParam("contentIdPrefix") String contentIdPrefix,
				  @PathParam("startId") String startId) {
		try {
			if (count <= 0) count = 10;
			this.contMatchContent(contentIdPrefix, startId, count);
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	@POST
	@Path("matchContent/{count}/{contentIdPrefix}")
        @Produces("application/json")
	public String matchContent(@PathParam("count") int count,
				  @PathParam("contentIdPrefix") String contentIdPrefix) {
		try {
			if (count <= 0) count = 10;
			this.contMatchContent(contentIdPrefix, contentIdPrefix, count);
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	@POST
	@Path("matchContent/{count}")
        @Produces("application/json")
	public String matchContent(@PathParam("count") int count) {
		try {
			if (count <= 0) count = 10;
			this.contMatchContent("", "", count);
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	
	@POST
	@Path("listEvents/{contentId}")
        @Produces("application/json")
	public String listEvents(@PathParam("contentId") String contentId) {
		try {
			this.contListContentEvents(contentId, "", 10);
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	@POST
	@Path("listEvents/{contentId}/{lastEventId}")
        @Produces("application/json")
	public String listEvents(@PathParam("contentId") String contentId, 
				 @PathParam("lastEventId") String lastEventId) {
		try {
			this.contListContentEvents(contentId, lastEventId, 10);
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	@POST
	@Path("listDeviceEntitlements/{deviceId}")
        @Produces("application/json")
	public String listDeviceEntitlements(@PathParam("deviceId") String deviceId) {
		try {
			this.entListDeviceEntitlements(deviceId);
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	@POST
	@Path("addDevice")
	@Consumes("application/json")
	@Produces("application/json")
	public String addDevice(Device dev) {
		try {
			this.devAddDevice(dev.getSmsNetworkId(), dev.getSmsDeviceId(), 
				dev.getDeviceType().toString(), dev.getNetworkDeviceId());
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	@POST
	@Path("removeDevice/{deviceId}/{networkId}")
	@Produces("application/json")
	public String removeDevice(@PathParam("deviceId") String deviceId, 
				   @PathParam("networkId") String networkId) {
		try {
			this.devRemoveDevice(networkId, deviceId, 
				"WEB_PC", "");
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	
	@POST
	@Path("addPackage")
	@Consumes("application/json")
	@Produces("application/json")
	public String addPackage(Package pkg) {
		try {
			this.entAddPackage(pkg.getSmsPackageId(), pkg.getDescription());
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	@POST
	@Path("removePackage/{packageId}")
	@Produces("application/json")
	public String removePackage(@PathParam("packageId") String packageId) {
		try {
			this.entRemovePackage(packageId);
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	@POST
	@Path("addVoD")
	@Consumes("application/json")
	@Produces("application/json")
	public String addVoD(VoDContent vc) {
		try {
			this.contAddVoD(
				vc.getNetworkId(), 
				vc.getContentId(), 
				vc.getDescription(), 
				vc.getResourceId(),
				vc.getPackageId()
			);
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}

		try {
			if (vc.getPackageId().equalsIgnoreCase("free")) {
				publishFreeVoD(vc.getContentId());
			}
			else if (vc.getPackageId().equalsIgnoreCase("subscription")) {
				publishSubscriptionVoD(vc.getContentId());
			}
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
		return responseBody;
	}
	@POST
	@Path("removeVoD")
	@Consumes("application/json")
	@Produces("application/json")
	public String removeVoD(VoDContent vc) {
		try {
			unpublishFreeVoD(vc.getContentId());
		}
		catch (Exception e) {}
		try {
			unpublishSubscriptionVoD(vc.getContentId());
		}
		catch (Exception e) {}
		
		try {
			this.contRemoveVoD(
				vc.getNetworkId(), 
				vc.getContentId(), 
				vc.getResourceId()
			);
				
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	@POST
	@Path("addPremiumVoD")
	@Consumes("application/json")
	@Produces("application/json")
	public String addPremiumVoD(VoDContent vc) {
		try {
			this.contAddPremiumVoD(
				vc.getNetworkId(), 
				vc.getContentId(), 
				vc.getDescription(), 
				vc.getResourceId()
			);
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}

		try {
			publishPremiumVoD(vc.getContentId());
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
		return responseBody;
	}
	@POST
	@Path("removePremiumVoD")
	@Consumes("application/json")
	@Produces("application/json")
	public String removePremiumVoD(VoDContent vc) {
		try {
			unpublishPremiumVoD(vc.getContentId());
		}
		catch (Exception e) {}
		
		try {
			this.contRemovePremiumVoD(
				vc.getNetworkId(), 
				vc.getContentId(), 
				vc.getResourceId()
			);
				
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	
	@POST
	@Path("entitleDeviceNew")
	@Consumes("application/json")
	@Produces("application/json")
	public String entitleDeviceNew(EntitlementInfo entInfo) {
		try {
			this.entEntitleDeviceToPackage(entInfo.getDeviceId(), entInfo.getPackageId(), entInfo.getEntTimeStr());
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}
	@POST
	@Path("entitleDevice/{deviceId}/{packageId}/{hours}")
	@Produces("application/json")
	public String entitleDevice(@PathParam("deviceId") String deviceId, 
				    @PathParam("packageId") String packageId, 
				    @PathParam("hours") int hours) {
		try {
			this.entEntitleDeviceToPackage(deviceId, packageId, hours);
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}

	@POST
	@Path("revokeDevice/{deviceId}/{packageId}")
	@Produces("application/json")
	public String revokeDevice(@PathParam("deviceId") String deviceId, 
				   @PathParam("packageId") String packageId) {
		try {
			this.entRevokeDeviceFromPackage(deviceId, packageId);
			return responseBody;
		}
		catch (Exception e) {
			return "{Success:false, reason:\"" + e.getMessage() + "\"}";
		}
	}

	@POST
	@Path("getSettings")
	@Produces("application/json")
	public Properties getSettings() {
		return config;
	}
		
	@Override
	protected String loadSession() {
		return sessionKey;
	}

	@Override
	protected void saveSession() {
		sessionKey = mSessionHandle;
	}

	@Override
	protected void feedback(String... strings) {}

	@Override
	protected void feedback(String string, StringBuilder sb) {
		responseBody = sb.toString();
	}

	private void publishVoD(String contentId, String target_dir_key) throws Exception {
		String tokens[] = contentId.split("_");
		String pathStr;
		pathStr = config.getProperty("vod_dir") + "/" +
					tokens[0] + "/" + contentId + ".mp4";
		java.nio.file.Path link = Paths.get(config.getProperty(target_dir_key), contentId + ".mp4");
		File vodFile = new File(pathStr);
		if (vodFile.exists() && vodFile.isFile())
			Files.createSymbolicLink(link, vodFile.toPath());
		else 
			throw new Exception("VoD file does not exists");
	}

	private void unpublishVoD(String contentId, String target_dir_key) throws Exception {
		java.nio.file.Path link = Paths.get(config.getProperty(target_dir_key), contentId + ".mp4");
		Files.delete(link);
	}
	private void publishFreeVoD(String contentId) throws Exception {
		addVoDToStreamMap(config.getProperty("free_stream_map"), contentId);
		publishVoD(contentId, "free_dir");
	}
	private void publishSubscriptionVoD(String contentId) throws Exception {
		addVoDToStreamMap(config.getProperty("subscription_stream_map"), contentId);
		publishVoD(contentId, "subscription_dir");
	}
	private void publishPremiumVoD(String contentId) throws Exception {
		addVoDToStreamMap(config.getProperty("premium_stream_map"), contentId);
		publishVoD(contentId, "premium_dir");
	}
	private void unpublishFreeVoD(String contentId) throws Exception {
		removeVoDFromStreamMap(config.getProperty("free_stream_map"), contentId);
		unpublishVoD(contentId, "free_dir");
	}
	private void unpublishSubscriptionVoD(String contentId) throws Exception {
		removeVoDFromStreamMap(config.getProperty("subscription_stream_map"), contentId);
		unpublishVoD(contentId, "subscription_dir");
	}
	private void unpublishPremiumVoD(String contentId) throws Exception {
		removeVoDFromStreamMap(config.getProperty("premium_stream_map"), contentId);
		unpublishVoD(contentId, "premium_dir");
	}
	private void addVoDToStreamMap(String streamMapFile, String contentId) throws Exception {
		PrintWriter pw = new PrintWriter(new FileWriter(streamMapFile, true));
		pw.println(contentId + ".mp4={resourceId:" + contentId + "}");
		pw.flush();
		pw.close();
	}
	private void removeVoDFromStreamMap(String streamMapFile, String contentId) throws Exception {
		File inFile = new File(streamMapFile);
      
		if (!inFile.isFile()) {
			throw new Exception(streamMapFile + " does not exist");
		}
		//Construct the new file that will later be renamed to the original filename.
		File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
		BufferedReader br = new BufferedReader(new FileReader(streamMapFile));
		PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
		
		//Read from the original file and write to the new
		//unless content matches data to be removed.
		String line = br.readLine();
		while ( (line != null) ) {
			int i = line.indexOf(contentId + ".mp4=");
			if( i != 0 ) {
				pw.println(line);
			}
			line = br.readLine();
		}
		pw.flush();
		pw.close();
		br.close();
		
		//Delete the original file
		if (!inFile.delete()) {
			throw new Exception("Cannot delete file");
		}
		
		//Rename the new file to the filename the original file had.
		if (!tempFile.renameTo(inFile))
			throw new Exception("Cannot rename file");	
	}
}