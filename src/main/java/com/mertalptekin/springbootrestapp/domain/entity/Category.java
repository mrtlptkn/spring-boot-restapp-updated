package com.mertalptekin.springbootrestapp.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data // Hem @Getter hemde @Setter görevi her bir field üzerinden tek tek yazmak yerine
// sınıf üzerine yazılarak genel olarak tüm fieldlar dışarı açılır. eğer tüm fieldları dışarı açmayacaksak
// bunu kullanmayız
@AllArgsConstructor // düm fieldalar constructor içinde set edilsin diye var
@NoArgsConstructor // boş consrtuctor
@Entity // veri tabanı bağlantısı olan bir nesne olduğunu @Entity anatasyonu ile belirttik.
@Table(name="categories")
public class Category {

    // boş contructor
//    public Category(){
//
//    }


    //  all contructor kısmı
//    public Category(Integer id, String name, List<Product> products){
//
//    }
//

    @Id // PK lanı olduğunu işaretler
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Id alanı Auto Increment çalışır
    //@Setter Eğer Data yazılı değilse setter sağlar
    //@Getter  Eğer Data yazılı değilse getter sağlar
    private Integer id;



    @Column(name="name", nullable = false, unique = true)
    @NotEmpty
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;


    // CascadeType.ALL: Bu ayar, Category üzerinde yapılan işlemlerin (örneğin, silme, güncelleme) ilişkili Product varlıklarına da uygulanmasını sağlar. Örneğin, bir Category silindiğinde, o kategoriye ait tüm Product varlıkları da otomatik olarak silinir.
    // FetchType.LAZY: Bu ayar, Category varlığı yüklendiğinde ilişkili Product varlıklarının hemen yüklenmemesini sağlar. Bunun yerine, products alanına erişildiğinde Product varlıkları veritabanından getirilir. Bu, performansı artırabilir çünkü gereksiz yere tüm ilişkili verilerin yüklenmesini önler.
    // CascadeType.Remove: Bu ayar, bir Category silindiğinde, o kategoriye ait tüm Product varlıklarının da otomatik olarak silinmesini sağlar.
    // Cascade.Merge: Bu ayar, bir Category güncellendiğinde, o kategoriye ait tüm Product varlıklarının da otomatik olarak güncellenmesini sağlar.

}
