package com.code.shopapp.service.image;

import com.code.shopapp.dto.ImageDto;
import com.code.shopapp.exceptions.RessourceNotFoundException;
import com.code.shopapp.model.Image;
import com.code.shopapp.model.Product;
import com.code.shopapp.repository.ImageRepository;
import com.code.shopapp.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()-> new RessourceNotFoundException("Image not found with id: "+id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository::delete, ()->{
                    throw new RessourceNotFoundException("No image found with id: "+id);
                });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById((productId));
        List<ImageDto> savedImageDtos = new ArrayList<>();
        String url = "/api/v1/images/image/download/";

        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String downloadUrl = url +image.getId();
                image.setDownloadUrl(downloadUrl);

                Image savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl(url +savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownLoadUrl(savedImage.getDownloadUrl());

                savedImageDtos.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return savedImageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getName());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
