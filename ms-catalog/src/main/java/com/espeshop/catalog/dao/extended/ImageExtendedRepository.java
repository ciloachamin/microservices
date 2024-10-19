package com.espeshop.catalog.dao.extended;

import com.espeshop.catalog.model.dtos.ImageFilterDto;
import com.espeshop.catalog.model.entities.Image;
import java.util.List;

public interface ImageExtendedRepository {
    List<Image> findAllImages(ImageFilterDto filters);
}
