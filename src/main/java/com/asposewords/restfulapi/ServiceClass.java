package com.asposewords.restfulapi;

import com.aspose.storage.api.StorageApi;
import com.aspose.storage.model.ResponseMessage;
import com.aspose.words.Document;
import com.aspose.words.ProtectionType;
import com.aspose.words.api.WordsApi;
import com.aspose.words.model.ProtectionData;
import com.aspose.words.model.ProtectionDataResponse;
import com.aspose.words.model.ProtectionRequest;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import net.sf.json.JSONObject;

@Path("/words")
public class ServiceClass {

    @PUT
    @Path("/convert")
    @Produces(MediaType.TEXT_PLAIN)
    public String convert(@Context HttpHeaders headers) throws Exception {

        String srcPath = headers.getRequestHeaders().getFirst("src");

        String format = headers.getRequestHeaders().getFirst("format");

        String destPath = "";
        try {
            destPath = srcPath.substring(0, srcPath.lastIndexOf("/")) + "/";
            destPath += "/";
        } catch (Exception ee) {
            destPath = srcPath.substring(0, srcPath.lastIndexOf("\\"));
            destPath += "\\";
        }
        Document doc = new Document(srcPath);

        destPath = destPath + "Converted." + format.toLowerCase();
        doc.save(destPath);
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("srcPath", srcPath);
        jsonObj.put("format", format);
        jsonObj.put("Saved At:", destPath);
        return jsonObj.toString();
    }

    @POST
    @Path("/protection")
    @Produces({ "application/json" })
    @Consumes("application/x-www-form-urlencoded")
    public String protection(@FormParam("src") String src) {

        String response = "";
        String destPath = "";
        String storage = null;
        String folder = null;
        String appSID = "91a2fd07-bba1-4b32-9112-abfb1fe8aebd";
        String apiKey = "0fbf678c5ecabdb5caca48452a736dd0";
        File srcfile = new File(src);
        String fileName = srcfile.getName();//source file 
        String destFileName = "updated-" + fileName;//destination updated file
        try {

            StorageApi storageApi = new StorageApi(apiKey, appSID, true); // Instantiate Aspose Storage API SDK
            WordsApi wordsApi = new WordsApi(apiKey, appSID, true);// Instantiate Aspose Words API SDK
            ProtectionRequest body = new ProtectionRequest();
            body.setPassword("123456789");
            body.setProtectionType(ProtectionType.getName(ProtectionType.READ_ONLY));

            storageApi.PutCreate(fileName, "", "", srcfile);//upload input file to aspose cloud storage

            // invoke Aspose.Words Cloud SDK API to protect a word document
            ProtectionDataResponse apiResponse = wordsApi.PutProtectDocument(fileName, destFileName, storage, folder, body);

            if (apiResponse != null && apiResponse.getStatus().equals("OK")) {

                response = apiResponse.toString();
                ResponseMessage storageRes = storageApi.GetDownload(destFileName, null, null); // download updated file from cloud storage
                InputStream responseStream = storageRes.getInputStream();//new FileInputStream(new File(src));//.getInputStream();

                try {
                    destPath = src.substring(0, src.lastIndexOf("/")) + "/";
                    destPath += "/";
                } catch (Exception ee) {
                    destPath = src.substring(0, src.lastIndexOf("\\"));
                    destPath += "\\";
                }
                final java.nio.file.Path destination = Paths.get(destPath + destFileName);

                Files.copy(responseStream, destination, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("srcPath", src);
        jsonObj.put("Saved At:", destPath);
        return jsonObj.toString();
}

    @GET
    @Path("/protection")
    @Produces(MediaType.TEXT_PLAIN)
    public String protection(@Context UriInfo info) throws Exception {
        ProtectionData docProtectionData = null;
        String src = info.getQueryParameters().getFirst("src");
        String appSID = "91a2fd07-bba1-4b32-9112-abfb1fe8aebd";
        String apiKey = "0fbf678c5ecabdb5caca48452a736dd0";
        try {
            File file = new File(src);
            String fileName = file.getName();
            String storage = null;
            String folder = null;
            
            StorageApi storageApi = new StorageApi(apiKey, appSID, true);// Instantiate Aspose Storage API SDK
            WordsApi wordsApi = new WordsApi(apiKey, appSID, true);// Instantiate Aspose Words API SDK
            storageApi.PutCreate(fileName, "", "", file);//upload input file to aspose cloud storage
            
            ProtectionDataResponse apiResponse = wordsApi.GetDocumentProtection(fileName, storage, folder);// invoke Aspose.Words Cloud SDK API to get current protection of a word document

            if (apiResponse != null&& apiResponse.getStatus().equals("OK")) {
                docProtectionData = apiResponse.getProtectionData();
                if (docProtectionData != null) {//display document protection info
                    System.out.println("Protection Type: " + docProtectionData.getProtectionType());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("srcPath", src);
        jsonObj.put("Protection Type:", docProtectionData.getProtectionType());
        return jsonObj.toString();
        
    }

}
