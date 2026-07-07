package com.mertalptekin.springbootrestapp.presentation.controller;

import com.mertalptekin.springbootrestapp.application.category.CategoryResponseDto;
import com.mertalptekin.springbootrestapp.application.category.CreateCategoryRequest;
import com.mertalptekin.springbootrestapp.application.category.ProductResponseDto;
import com.mertalptekin.springbootrestapp.domain.entity.Category;
import com.mertalptekin.springbootrestapp.domain.entity.Product;
import com.mertalptekin.springbootrestapp.infra.repository.ICategoryRepository;
import com.mertalptekin.springbootrestapp.infra.repository.IProductRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final ICategoryRepository categoryRepository;
    private final IProductRepository productRepository;
    private final ModelMapper modelMapper; // Genel olarak bu işlemler Application Layerda yazılır.

    public CategoryController(ICategoryRepository categoryRepository, IProductRepository productRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    // api/categories -> tüm kategorileri getirir
    // api/categories?id=5 -> tek bir kategoriyi getirir


    // GetMapping isteklerinde entityden dto üretilir (response-dto)
    // PostMapping, PutMapping, PatchMapping işlemlerinde ise dtodan (request) -> modelmapper entity (save)

    @GetMapping
    public ResponseEntity<Object> getCategories(@RequestParam(required = false) Integer id) {

//        List<Category> categories = categoryRepository.findAll();
//        return  ResponseEntity.ok(categories);

        // entity ilişkilerinden dolayı bunu bu şekilde yazmıyoruz. Dto ile cevap döndürmemiz lazım.

        // mapper sınıfları sayesinde entity dto çevrilir.
        if (id == null) {
            List<CategoryResponseDto> categories = categoryRepository.findAll().stream()
                    .map(category -> modelMapper.map(category, CategoryResponseDto.class))
                    .toList();
            return ResponseEntity.ok(categories);
        }

       Optional<Category> category = categoryRepository.findById(id);

       // eğer üzerinde çalıştığım nesnede sadece kategoriye ait alanlanlar üzerinde çalışacak isek bu durumda lazy fetch kullanabiliriz. Daha performanslı olur.
        // ama kategori ile birlikte kategoriye ait ürünler üzerinde de çalışacak isek bu durumda eager fetch kullanmak daha mantıklı olur.

        // category nesnesi sistemde mevcut ise
       if(category.isPresent()) {
           return ResponseEntity.ok("Category Name: " + category.get().getName());
       } else {
           return ResponseEntity.notFound().build(); // 404 döndür.
       }
    }


    // @RequestBody ile veri gönderme json tipinde
    // @Valid -> Post ederken Put ederken verşileri serverside validation kavramdan geçirip daha sonra
    // doğru bir şekilde entity dönüştürürüz.

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CreateCategoryRequest request) {

        // Entity mutable olduğunda setter var.
        // record dtodan gelen bilgi entity set edilir.
        Category category = new Category();
        category.setName(request.name());
        // controller katmanı dto ile çalışır.
        // request.name("Ali"); record immutable olduğundan sadece okunur setter yoktur.
        // set işlemi RequestBody üzerinden clienttan gelir.
        // POST ve PUT ve PATCH işlemlerinde record tercih edir.
        // Recordlar aynı zamanda Event güdümlü programlama modeli içinde oldukça kullanışlıdır.
        // Eventlerde immutable çalışır


        // jpa katmanı üzerinden veri tabanına entity olarak gönderilir.
        // repository katmanı entity ile çalışır.
        Category saved = categoryRepository.save(category);

        CategoryResponseDto response = modelMapper.map(saved, CategoryResponseDto.class);
        URI uri = URI.create("/api/v1/categories/" + saved.getId());

        // Response entity üzerinden 201 döndürme şeklidir.
        return ResponseEntity.created(uri).body(response);
    }


    @PutMapping("{id}")
    public  ResponseEntity<Object> update(@PathVariable Integer id,@RequestParam CategoryResponseDto request){
        if(!categoryRepository.existsById(id)){
            return ResponseEntity.notFound().build(); // 404
        } else {
            Category entity =  modelMapper.map(request,Category.class);
            categoryRepository.save(entity);
            return ResponseEntity.noContent().build(); // 204
        }
    }

    //api/v1/categories/withProducts
    @GetMapping("/withProducts")
    public ResponseEntity<Category> getCategoriesWithProducts(@RequestParam(required = false) Integer id) {
         Optional<Category> category = categoryRepository.findWithProductsByCategoryId(id);

         if(category.isPresent()) {
              return ResponseEntity.ok(category.get());
         } else {
              return ResponseEntity.notFound().build();
         }
    }

    @GetMapping("/withProductsDtoVersion")
    public ResponseEntity<CategoryResponseDto> getCategoriesWithProductsDto(@RequestParam(required = false) Integer id) {

        Optional<Category> entity = categoryRepository.findWithProductsByCategoryId(id);
        if(entity.isPresent()) {
            CategoryResponseDto responseDto = modelMapper.map(entity.get(), CategoryResponseDto.class);
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // api/categories/5 -> DELETE
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    // Products Entity

    @GetMapping("/findProductBetweenPrices")
    public ResponseEntity<List<Product>> findProductBetweenPrices(@RequestParam Integer min,@RequestParam Integer max) {
        List<Product> products = productRepository.findByPriceBetween(BigDecimal.valueOf(min), BigDecimal.valueOf(max));

        return ResponseEntity.ok(products);

    }

    @GetMapping("/findProductBetweenPricesDtoVersion")
    public ResponseEntity<List<ProductResponseDto>> findProductBetweenPricesDto(@RequestParam Integer min, @RequestParam Integer max) {
        List<ProductResponseDto> products = productRepository.findByPriceBetween(BigDecimal.valueOf(min), BigDecimal.valueOf(max)).stream().map(product -> modelMapper.map(product, ProductResponseDto.class)).toList();

        return ResponseEntity.ok(products);

    }


    @GetMapping("/paginationAndSorting")
    public ResponseEntity<Page<Product>> findProductBetweenPricesWithPage(@RequestParam Integer page, @RequestParam Integer size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("price").ascending().and(Sort.by("name").descending()));
        Page<Product> pageModel = productRepository.findAll(
                    pageable);

        System.out.println("totalPages" + pageModel.getTotalPages());
        System.out.println("totalElements" + pageModel.getTotalElements());
        System.out.println("size" + pageModel.getSize());

        return ResponseEntity.ok(pageModel);
    }

}
