package com.example.product.config;

import com.example.product.entity.Category;
import com.example.product.entity.Product;
import com.example.product.entity.ProductCategory;
import com.example.product.entity.User;
import com.example.product.repository.CategoryRepository;
import com.example.product.repository.ProductCategoryRepository;
import com.example.product.repository.ProductRepository;
import com.example.product.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(
            CategoryRepository categoryRepository,
            ProductRepository productRepository,
            ProductCategoryRepository productCategoryRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        seedAdmin();

        List<Category> categories = seedCategories();
        List<Product> products = seedProducts();

        seedProductCategories(categories, products);
    }

    private void seedAdmin() {

        if (userRepository.existsByUsername("admin")) {
            return;
        }

        User admin = new User();

        admin.setUsername("admin");
        admin.setPassword(
                passwordEncoder.encode("123456")
        );
        admin.setRole("ADMIN");
        admin.setEnabled(true);

        userRepository.save(admin);
    }

    private List<Category> seedCategories() {

        if (categoryRepository.count() > 0) {
            return categoryRepository.findAll();
        }

        Category c1 = new Category();
        c1.setName("Điện thoại");
        c1.setDescription("Các loại điện thoại");
        c1.setCategoryCode("PHONE");
        c1.setStatus("ACTIVE");
        c1.setCreatedBy("admin");
        c1.setModifiedBy("admin");

        Category c2 = new Category();
        c2.setName("Laptop");
        c2.setDescription("Các loại laptop");
        c2.setCategoryCode("LAPTOP");
        c2.setStatus("ACTIVE");
        c2.setCreatedBy("admin");
        c2.setModifiedBy("admin");

        Category c3 = new Category();
        c3.setName("Máy tính bảng");
        c3.setDescription("Các loại máy tính bảng");
        c3.setCategoryCode("TABLET");
        c3.setStatus("ACTIVE");
        c3.setCreatedBy("admin");
        c3.setModifiedBy("admin");

        Category c4 = new Category();
        c4.setName("Phụ kiện");
        c4.setDescription("Các loại phụ kiện");
        c4.setCategoryCode("ACCESSORY");
        c4.setStatus("ACTIVE");
        c4.setCreatedBy("admin");
        c4.setModifiedBy("admin");

        Category c5 = new Category();
        c5.setName("Âm thanh");
        c5.setDescription("Các thiết bị âm thanh");
        c5.setCategoryCode("AUDIO");
        c5.setStatus("ACTIVE");
        c5.setCreatedBy("admin");
        c5.setModifiedBy("admin");

        return categoryRepository.saveAll(
                List.of(c1, c2, c3, c4, c5)
        );
    }

    private List<Product> seedProducts() {

        if (productRepository.count() > 0) {
            return productRepository.findAll();
        }

        Date now = new Date();

        Product p1 = new Product();
        p1.setName("iPhone 15");
        p1.setImage("https://example.com/images/iphone15.jpg");
        p1.setDescription("Điện thoại Apple iPhone 15");
        p1.setPrice(19990000.0);
        p1.setProductCode("SP001");
        p1.setQuantity(100L);
        p1.setStatus("ACTIVE");
        p1.setCreatedDate(now);
        p1.setModifiedDate(now);
        p1.setCreatedBy("admin");
        p1.setModifiedBy("admin");

        Product p2 = new Product();
        p2.setName("Samsung Galaxy S24");
        p2.setImage("https://example.com/images/samsung-s24.jpg");
        p2.setDescription("Điện thoại Samsung Galaxy S24");
        p2.setPrice(18990000.0);
        p2.setProductCode("SP002");
        p2.setQuantity(80L);
        p2.setStatus("ACTIVE");
        p2.setCreatedDate(now);
        p2.setModifiedDate(now);
        p2.setCreatedBy("admin");
        p2.setModifiedBy("admin");

        Product p3 = new Product();
        p3.setName("MacBook Air M3");
        p3.setImage("https://example.com/images/macbook-air-m3.jpg");
        p3.setDescription("Laptop Apple MacBook Air M3");
        p3.setPrice(25990000.0);
        p3.setProductCode("SP003");
        p3.setQuantity(50L);
        p3.setStatus("ACTIVE");
        p3.setCreatedDate(now);
        p3.setModifiedDate(now);
        p3.setCreatedBy("admin");
        p3.setModifiedBy("admin");

        Product p4 = new Product();
        p4.setName("iPad Air");
        p4.setImage("https://example.com/images/ipad-air.jpg");
        p4.setDescription("Máy tính bảng Apple iPad Air");
        p4.setPrice(16990000.0);
        p4.setProductCode("SP004");
        p4.setQuantity(70L);
        p4.setStatus("ACTIVE");
        p4.setCreatedDate(now);
        p4.setModifiedDate(now);
        p4.setCreatedBy("admin");
        p4.setModifiedBy("admin");

        Product p5 = new Product();
        p5.setName("AirPods Pro 2");
        p5.setImage("https://example.com/images/airpods-pro-2.jpg");
        p5.setDescription("Tai nghe không dây Apple AirPods Pro 2");
        p5.setPrice(5990000.0);
        p5.setProductCode("SP005");
        p5.setQuantity(120L);
        p5.setStatus("ACTIVE");
        p5.setCreatedDate(now);
        p5.setModifiedDate(now);
        p5.setCreatedBy("admin");
        p5.setModifiedBy("admin");

        return productRepository.saveAll(
                List.of(p1, p2, p3, p4, p5)
        );
    }

    private void seedProductCategories(
            List<Category> categories,
            List<Product> products
    ) {

        if (productCategoryRepository.count() > 0) {
            return;
        }

        Category phone = findByCode(
                categories,
                Category::getCategoryCode,
                "PHONE"
        );

        Category laptop = findByCode(
                categories,
                Category::getCategoryCode,
                "LAPTOP"
        );

        Category tablet = findByCode(
                categories,
                Category::getCategoryCode,
                "TABLET"
        );

        Category audio = findByCode(
                categories,
                Category::getCategoryCode,
                "AUDIO"
        );

        Product iphone = findByCode(
                products,
                Product::getProductCode,
                "SP001"
        );

        Product samsung = findByCode(
                products,
                Product::getProductCode,
                "SP002"
        );

        Product macbook = findByCode(
                products,
                Product::getProductCode,
                "SP003"
        );

        Product ipad = findByCode(
                products,
                Product::getProductCode,
                "SP004"
        );

        Product airpods = findByCode(
                products,
                Product::getProductCode,
                "SP005"
        );

        Date now = new Date();

        ProductCategory pc1 =
                newProductCategory(iphone, phone, now);

        ProductCategory pc2 =
                newProductCategory(samsung, phone, now);

        ProductCategory pc3 =
                newProductCategory(macbook, laptop, now);

        ProductCategory pc4 =
                newProductCategory(ipad, tablet, now);

        ProductCategory pc5 =
                newProductCategory(airpods, audio, now);

        productCategoryRepository.saveAll(
                List.of(pc1, pc2, pc3, pc4, pc5)
        );
    }

    private ProductCategory newProductCategory(
            Product product,
            Category category,
            Date now
    ) {

        ProductCategory pc = new ProductCategory();

        pc.setProduct(product);
        pc.setCategory(category);
        pc.setCreatedDate(now);
        pc.setModifiedDate(now);
        pc.setCreatedBy("admin");
        pc.setModifiedBy("admin");

        return pc;
    }

    private <T> T findByCode(
            List<T> items,
            java.util.function.Function<T, String> codeGetter,
            String code
    ) {

        return items.stream()
                .filter(item ->
                        code.equals(codeGetter.apply(item))
                )
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Seed data not found for code: " + code
                        )
                );
    }
}