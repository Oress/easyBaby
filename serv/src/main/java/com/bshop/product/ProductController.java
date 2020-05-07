package com.bshop.product;

import com.bshop.ProductImage.model.ProductImage;
import com.bshop.ProductImage.model.ProductImageRepository;
import com.bshop.inventoryItem.assembler.InventoryItemAssembler;
import com.bshop.inventoryItem.model.InventoryItem;
import com.bshop.inventoryItem.model.InventoryItemRepository;
import com.bshop.inventoryItem.model.InventoryItemStatus;
import com.bshop.product.assembler.ProductAssembler;
import com.bshop.product.dto.CartFull;
import com.bshop.product.dto.CartItemFull;
import com.bshop.product.dto.ProductFull;
import com.bshop.product.dto.ProductShort;
import com.bshop.product.model.Product;
import com.bshop.product.model.ProductRepository;
import com.bshop.product.model.Product_;
import com.bshop.productCategory.model.ProductCategory;
import com.bshop.productCategory.model.ProductCategoryRepository;
import com.bshop.productChar.model.ProductChar;
import com.bshop.productChar.model.ProductCharRepository;
import com.bshop.productCharValue.dto.ProductCharValueFull;
import com.bshop.productCharValue.model.ProductCharValue;
import com.bshop.productCharValue.model.ProductCharValueRepository;
import com.bshop.user.model.User;
import com.bshop.user.model.UserRepository;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mysql.cj.util.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    private ProductRepository productRepository;
    private ProductImageRepository productImageRepository;
    private UserRepository userRepository;
    private ProductCharRepository productCharRepository;
    private ProductCharValueRepository productCharValueRepository;
    private ProductCategoryRepository productCategoryRepository;
    private InventoryItemRepository inventoryItemRepository;

    private ProductAssembler productAssembler = new ProductAssembler();
    private InventoryItemAssembler inventoryItemAssembler = new InventoryItemAssembler();

    private final String IMG_DIRECTORY = "data";
    private final String IMG_SMALL_SUFFIX = "_small";

    public ProductController(ProductRepository productRepository, ProductImageRepository productImageRepository, UserRepository userRepository, ProductCharRepository productCharRepository, ProductCharValueRepository productCharValueRepository, ProductCategoryRepository productCategoryRepository, InventoryItemRepository inventoryItemRepository) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.userRepository = userRepository;
        this.productCharRepository = productCharRepository;
        this.productCharValueRepository = productCharValueRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.inventoryItemRepository = inventoryItemRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    public List<ProductShort> getAllProductsShort(@RequestParam Integer page, @RequestParam Integer size) {
        List<ProductShort> result = Lists.newArrayList();

        Sort defaultSorting = Sort.by(Product_.TITLE).ascending();
        List<Product> productList = Lists.newArrayList(productRepository.findAll(PageRequest.of(page, size, defaultSorting)));
        result = productList.stream().map(model -> productAssembler.mapModelToProductShort(model)).collect(Collectors.toList());

        return result;
    }

    @Transactional
    @RequestMapping(path = "byId", method = RequestMethod.GET)
    public ProductFull getProduct(@RequestParam Integer id) {
        ProductFull result = null;

        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            result = productAssembler.mapModelToProductFull(productOptional.get());
        }

        return result;
    }

    @Transactional
    @RequestMapping(path = "byCategory", method = RequestMethod.GET)
    public List<ProductShort> getByCategory(@RequestParam Integer id) {
        List<ProductShort> result = Lists.newArrayList();

        List<Product> list = productRepository.findByCategoryId(id);
        result = list.stream().map(productAssembler::mapModelToProductShort).collect(Collectors.toList());
        return result;
    }

    @RequestMapping(path = "count", method = RequestMethod.GET)
    public Long getTotalCount() {
        return productRepository.count();
    }

    // TODO: change return result
    // TODO: Validation logic
    // TODO: 05.04.2020 Check for non existing productChars ?
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public void addNewProducts(@RequestBody List<ProductFull> productsToAdd) {
        if (productsToAdd != null) {
            List<Product> newProducts = Lists.newArrayList();

            for (ProductFull prodDto : productsToAdd) {
                Product newProduct = productAssembler.mapFullToPartialModel(prodDto);
                productRepository.save(newProduct);

                if (prodDto.productCharValues != null) {
                    List<ProductCharValue> prodCharsValues = Lists.newArrayList();

                    for (ProductCharValueFull productCharValueFull : prodDto.productCharValues) {
                        Optional<ProductChar> productChar = productCharRepository.findById(productCharValueFull.productCharId);
                        prodCharsValues.add(new ProductCharValue(null, productChar.get(), productCharValueFull.value, newProduct));
                    }

                    productCharValueRepository.saveAll(prodCharsValues);
                    newProduct.setProductCharValues(prodCharsValues);
                }
                if (prodDto.categoryIds != null) {
                    List<ProductCategory> categories = Lists.newArrayList(productCategoryRepository.findAllById(prodDto.categoryIds));
                    newProduct.setProductCategories(categories);
                }

                newProducts.add(newProduct);
            }
//            productRepository.saveAll(newProducts);
        }
    }

    @Transactional
    @RequestMapping(path = "/img", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> saveOrUpdate(@RequestParam String uid) throws IOException {
        byte[] result = null;
        if (!Strings.isNullOrEmpty(uid)) {
            Path path = Paths.get(IMG_DIRECTORY + "/" + uid);
            result = Files.readAllBytes(path);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
        ResponseEntity responseEntity = new ResponseEntity(result, headers, HttpStatus.OK);

        return responseEntity;
    }

    @Transactional
    @RequestMapping(path = "/uploadImages", method = RequestMethod.POST)
    public void uploadImages(@RequestParam("imgs") List<MultipartFile> files, @RequestParam Integer prodid) {
        if (!files.isEmpty()) {
            Product prod = productRepository.findById(prodid).get();

            List<ProductImage> images = prod.getImages();
            files.forEach(file -> {
                try {
                    ProductImage img = saveImage(file.getBytes());
                    productImageRepository.save(img);
                    images.add(img);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            productRepository.save(prod);
        }
    }

    @Transactional
    @RequestMapping(path = "/saveOrUpdate", method = RequestMethod.POST)
    public ProductFull saveOrUpdate(@RequestBody ProductFull productToUpdate) {
        ProductFull updateResult = null;

        if (productToUpdate != null) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> optional = userRepository.findByUsername(username);

            if (optional.isPresent()) {
                Integer recid = productToUpdate.id;
                User user = optional.get();

                if (recid != null) {
                    Optional<Product> optionalProduct = productRepository.findById(recid);
                    Product updated = updateProduct(optionalProduct.get(), productToUpdate, user);
                    updateResult = productAssembler.mapModelToProductFull(updated);
                } else {
                    Product newProduct = productAssembler.mapFullToPartialModel(productToUpdate);

                    newProduct.setAddedBy(user);
                    newProduct.setRegistrationDate(new Date());
//                    newProduct.setProductCategories(user);

                    if (productToUpdate.categoryIds != null) {
                        List<ProductCategory> categories = Lists.newArrayList(productCategoryRepository.findAllById(productToUpdate.categoryIds));
                        newProduct.setProductCategories(categories);
                    }

                    productRepository.save(newProduct);

                    if (productToUpdate.inventoryItems != null && !productToUpdate.inventoryItems.isEmpty()) {
                        Date regDT = new Date();
                        List<InventoryItem> toUpdate = productToUpdate.inventoryItems
                                .stream()
                                .map(inventoryItemAssembler::assemblePartialModel)
                                .collect(Collectors.toList());

                        toUpdate.forEach(item -> {
                            item.setProduct(newProduct);
                            item.setAddedBy(user);
                            item.setStatus(InventoryItemStatus.AVAILABLE);
                            item.setRegistrationDate(regDT);
                        });
                        inventoryItemRepository.saveAll(toUpdate);
                        newProduct.setInventoryItems(toUpdate);
                    }
                    productRepository.save(newProduct);
                    updateResult = productAssembler.mapModelToProductFull(newProduct);
                }
            }
        }
        return updateResult;
    }

    @Transactional
    @RequestMapping(path = "/cart", method = RequestMethod.POST)
    public CartFull updateCartInfo(@RequestBody List<CartItemFull> cartItems) {
        List<CartItemFull> products = Lists.newArrayList();

        CartFull result = new CartFull(products);

        if (cartItems != null) {
            for (CartItemFull cartItem : cartItems) {
                Integer productId = cartItem.productId;
                Integer productCount = cartItem.count;

                Product product = productRepository.findById(productId).get();
                List<InventoryItem> inventoryItems = inventoryItemRepository.findByProductId(productId, PageRequest.of(0, 20));

                String img = null;
                if (product.getImages() != null && product.getImages().size() > 0) {
                    img = product.getImages().get(0).getPath_small();
                }
                CartItemFull item = new CartItemFull(null, productId, product.getTitle(), img, productCount,
                        product.getPrice(), productCount * product.getPrice());
                item.isAvailable = inventoryItems.size() >= cartItem.count;
                products.add(item);
            }
        }

        Double totalPrice = products.stream()
                .filter(item -> item.isAvailable)
                .map(item -> item.totalPrice).reduce(0d, Double::sum);
        result.totalPrice = totalPrice;

        return result;
    }

    @Transactional
    @RequestMapping(path = "/favs", method = RequestMethod.GET)
    public List<ProductShort> getFavs(@RequestParam List<Integer> prodIds) {
        List<ProductShort> result = Lists.newArrayList();

        if (prodIds != null && prodIds.size() > 0) {
            List<Product> products = Lists.newArrayList(productRepository.findAllById(prodIds));
            result = products.stream().map(productAssembler::mapModelToProductShort).collect(Collectors.toList());
        }

        return result;
    }

    @Transactional
    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public List<ProductShort> search(@RequestParam String query) {
        List<ProductShort> result = Lists.newArrayList();

        if (!StringUtils.isNullOrEmpty(query)) {
            List<Product> productsByTitle = this.productRepository.findByTitleContaining(query, PageRequest.of(0, 30));

            result = productsByTitle.stream().map(productAssembler::mapModelToProductShort).collect(Collectors.toList());
        }

        return result;
    }

    @Transactional
    @RequestMapping(path = "/main", method = RequestMethod.GET)
    public List<ProductShort> mainPage() {
        List<ProductShort> result;

        List<Product> products = productRepository.findAll(PageRequest.of(0, 20)).getContent();
        result = products.stream().map(productAssembler::mapModelToProductShort).collect(Collectors.toList());
        return result;
    }

    private Product updateProduct(Product product, ProductFull dto, User user) {
        if (!Strings.isNullOrEmpty(dto.title)) {
            product.setTitle(dto.title);
        }
        if (!Strings.isNullOrEmpty(dto.description)) {
            product.setDescription(dto.description);
        }
        // TODO: VAlidate price
        if (dto.price != null) {
            product.setPrice(dto.price);
        }
        if (dto.categoryIds != null) {
            List<ProductCategory> categories = Lists.newArrayList(productCategoryRepository.findAllById(dto.categoryIds));
            product.setProductCategories(categories);
        }
        if (dto.inventoryItems != null && !dto.inventoryItems.isEmpty()) {
            Date regDT = new Date();
            List<InventoryItem> toUpdate = dto.inventoryItems
                    .stream()
                    .filter(item -> InventoryItemStatus.ADDED.equals(item.status))
                    .map(inventoryItemAssembler::assemblePartialModel)
                    .collect(Collectors.toList());

            toUpdate.forEach(item -> {
                item.setProduct(product);
                item.setAddedBy(user);
                item.setStatus(InventoryItemStatus.AVAILABLE);
                item.setRegistrationDate(regDT);
            });
            inventoryItemRepository.saveAll(toUpdate);
            product.getInventoryItems().addAll(toUpdate);


            List<Integer> toRemoveIds = dto.inventoryItems
                    .stream()
                    .filter(item -> InventoryItemStatus.TOREMOVE.equals(item.status))
                    .map(item -> item.id)
                    .collect(Collectors.toList());

            if (!toRemoveIds.isEmpty()) {
                List<InventoryItem> toRemove = Lists.newArrayList(inventoryItemRepository.findAllById(toRemoveIds));
                toRemove.forEach(item -> item.setStatus(InventoryItemStatus.REMOVED));
                inventoryItemRepository.saveAll(toRemove);
            }
        }

        if (dto.images != null) {
            List<ProductImage> images = product.getImages();
            Set<Integer> remaining = dto.images.stream().map(img -> img.id).collect(Collectors.toSet());
            List<ProductImage> toRemove = images.stream().filter(img -> !remaining.contains(img.getId())).collect(Collectors.toList());

            for (ProductImage img : toRemove) {
                try {
                    removeImage(img);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            productImageRepository.deleteAll(toRemove);

            List<ProductImage> toSave = images.stream().filter(img -> remaining.contains(img.getId())).collect(Collectors.toList());
            product.setImages(toSave);
        }


        productRepository.save(product);
        return product;
    }

    private ProductImage saveImage(byte[] bytes) throws IOException {
        String imageName = UUID.randomUUID().toString();
        String imageNameSmall = imageName + IMG_SMALL_SUFFIX;
        ProductImage productImage = new ProductImage(null, imageName, imageNameSmall);
        Path path = Paths.get(IMG_DIRECTORY + "/" + imageName);
        Files.write(path, bytes);

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));

        File compressedImageFile = new File(IMG_DIRECTORY + "/" + imageNameSmall);
        OutputStream os = new FileOutputStream(compressedImageFile);

        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.05f);

        writer.write(null, new IIOImage(image, null, null), param);
        os.close();
        ios.close();
        writer.dispose();
        return productImage;
    }

    private boolean removeImage(ProductImage productImage) throws IOException {
        boolean result = false;
        if (productImage.getId() != null) {
            String imgPath = IMG_DIRECTORY + "/" + productImage.getPath();
            String imgPathSmall = IMG_DIRECTORY + "/" + productImage.getPath_small();

            Path filePath = Paths.get(imgPath);
            Path filePathSmall = Paths.get(imgPathSmall);

            Files.delete(filePath);
            Files.delete(filePathSmall);

            result = true;
        }
        return result;
    }

}
