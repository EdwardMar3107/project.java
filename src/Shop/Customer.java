package Shop;

public class Customer {

    private String name;
    private Cart cart;

    public Customer(String name) {
        this.name = name;
        this.cart = new Cart();
    }

    public void addToCart(Product product) {
        cart.addProduct(product);
    }

    public void checkout() {
        System.out.println("🧾 Покупатель: " + name);
        cart.showCart();
        System.out.println("💰 Итого: " + cart.calculateTotal() + "₽");
    }
}
