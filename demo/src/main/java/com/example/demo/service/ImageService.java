//package com.example.demo.service;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.model.Image;
//import com.example.demo.model.User;
//import com.example.demo.repository.ImageRepository;
//import com.example.demo.repository.UserRepository;
//
////import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
//import okhttp3.*;
////import okhttp3.OkHttpClient;
////import okhttp3.RequestBody;
////import okhttp3.Response;
//import org.json.JSONObject;
//
//@Service
//public class ImageService {
//	 @Value("${imgur.client-id}")
//	    private String clientId;
//    private static final String IMGUR_API_URL = "https://api.imgur.com/3/image";
//    private static final String IMGUR_CLIENT_ID = "YOUR_IMGUR_CLIENT_ID";
//
//    @Autowired
//    private ImageRepository imageRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private final OkHttpClient client = new OkHttpClient();
//
//    public Image uploadImage(String imageBase64, Long userId) throws IOException {
//        Optional<User> userOptional = userRepository.findById(userId);
//        if (userOptional.isEmpty()) {
//            throw new RuntimeException("User not found");
//        }
//
//        User user = userOptional.get();
//
//        
//        RequestBody requestBody = new FormBody.Builder()
//                .add("image", imageBase64)
//                .build();
//
//        
//        Request request = new Request.Builder()
//                .url(IMGUR_API_URL)
//                .post(requestBody)
//                .addHeader("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
//                .build();
//
//        
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful()) {
//        	 String responseBody = response.body().string();
//        	 System.out.println("Imgur API upload failed: " + responseBody);
//            throw new IOException("Imgur API upload failed: " + response.message());
//        }
//
//       
//        JSONObject jsonResponse = new JSONObject(response.body().string());
//        JSONObject data = jsonResponse.getJSONObject("data");
//        String imageUrl = data.getString("link");
//        String deleteHash = data.getString("deletehash");
//
//       
//        Image image = new Image();
//        image.setUrl(imageUrl);
//        image.setDeleteHash(deleteHash);
//        image.setUser(user);
//        imageRepository.save(image);
//
//        return image;
//    }
//
//    public List<Image> getImagesByUser(Long userId) {
//        return imageRepository.findByUserId(userId);
//    }
//
//    public String deleteImage(Long imageId, Long userId) throws IOException {
//        Optional<Image> imageOptional = imageRepository.findById(imageId);
//        if (imageOptional.isEmpty()) {
//            throw new RuntimeException("Image not found");
//        }
//
//        Image image = imageOptional.get();
//        if (!image.getUser().getId().equals(userId)) {
//            throw new RuntimeException("Unauthorized to delete this image");
//        }
//
//        Request request = new Request.Builder()
//                .url(IMGUR_API_URL + "/" + image.getDeleteHash())
//                .delete()
//                .addHeader("Authorization", "Client-ID " + clientId)
//                .build();
//
//        
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful()) {
//            throw new IOException("Imgur API delete failed: " + response.message());
//        }
//
//        imageRepository.delete(image);
//        return "Image deleted successfully";
//    }
//}
