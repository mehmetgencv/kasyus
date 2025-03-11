package com.kasyus.product_service.bulk;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bulkProducts")
public class BulkProductController {

    private final BulkProductService bulkProductService;

    public BulkProductController(BulkProductService bulkProductService) {
        this.bulkProductService = bulkProductService;
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> bulkInsertProducts(@RequestBody List<BulkProductCreateRequest> products) {
        bulkProductService.saveProducts(products);
        return ResponseEntity.ok("Toplu ürün ekleme başarılı!");
    }

    @PostMapping("/bulkCategories")
    public ResponseEntity<String> bulkInsertCategories(@RequestBody List<BulkCategoryCreateRequest> categories) {
        bulkProductService.saveCategories(categories);
        return ResponseEntity.ok("Toplu kategori ekleme başarılı!");
    }
}
