package com.mertalptekin.springbootrestapp.application.product.discountPrice;

import com.mertalptekin.springbootrestapp.domain.entity.Product;
import com.mertalptekin.springbootrestapp.domain.entity.ProductPrice;
import com.mertalptekin.springbootrestapp.infra.repository.IProductPriceRepository;
import com.mertalptekin.springbootrestapp.infra.repository.IProductRepository;
import com.mertalptekin.springbootrestapp.infra.service.IEmailSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class DiscountPriceListener {

    // Method eventin fırlatıldığı anı dinler.
    private IProductPriceRepository productPriceRepository;
    private IEmailSender emailSender;
    private IProductRepository productRepository;

    public DiscountPriceListener(IProductPriceRepository productPriceRepository, @Qualifier("sendGrid") IEmailSender emailSender,IProductRepository productRepository) {
        this.productRepository = productRepository;
        this.productPriceRepository = productPriceRepository;
        this.emailSender = emailSender;
    }

    // @TransactionalEventListener dediğimizde ProductPrice kaydı yapamıyoruz.

    // ProductPrice History insert işlemi

    // @EventListener
    // Fakat bu TransactionEventListener olduğu için bunun çağırıldığı yerdeki transactionın commit olmasına bakar. Commit edilmeyen transactionlar için bu method çağırılmaz.
    // @TransactionalEventListener // TransactionalEventListener, eventin tetiklendiği transaction başarılı bir şekilde tamamlandığında listener metodunu çağırır.
    @EventListener
    public void discountPrice(DiscountPriceEvent event) {


        ProductPrice productPrice = new ProductPrice();
            productPrice.setOldPrice(event.oldPrice);
            productPrice.setNewPrice(event.newPrice);
            productPrice.setProduct((Product)event.getSource()); // İlgili ürünü atama
            productPrice.setChangedAt(java.time.LocalDateTime.now()); // Değişim zamanı atan
            // productPrice kaydet
            this.productPriceRepository.save(productPrice); // Bunu listenerda yapıcaz.


    }

    // Bunu tetikleyen methodda eğer hata oluşsa bile email gönderme işlemi tetiklenir.
    @EventListener
    public void SendEmail(DiscountPriceEvent event) {
        this.emailSender.sendEmail("test@test.com","Price Change Notification","The price has changed from " + event.oldPrice + " to " + event.newPrice);
    }


}
