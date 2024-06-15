package com.choiteresa.fonation.domain.attachment.entity;
import com.choiteresa.fonation.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;

    @Column
    public String type;

    @Column
    public String path;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    public Product product;

    public String getFilename(){
        String[] args = path.split("/");
        return args[args.length-1];
    }

    public void setForm(Product product){
        this.product = product;
    }
}