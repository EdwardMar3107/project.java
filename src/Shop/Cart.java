package Shop;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Product> products;

    public Cart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public double calculateTotal() {
        double total = 0;
        for (Product p : products) {
            total += p.getPrice();
        }
        return total;
    }

    public void showCart() {
        for (Product p : products) {
            System.out.println(p.getName() + ": " + p.getPrice() + "₽");
        }
    }
}
