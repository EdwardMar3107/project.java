package by.ezer.mappers;

import by.ezer.dto.OrderDTO;
import by.ezer.dto.ProductDTO;
import by.ezer.entity.Order;
import by.ezer.entity.Product;
import by.ezer.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-04T19:14:51+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public OrderDTO toDto(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDTO.OrderDTOBuilder orderDTO = OrderDTO.builder();

        orderDTO.userName( orderUserUsername( order ) );
        orderDTO.userEmail( orderUserEmail( order ) );
        orderDTO.id( order.getId() );
        orderDTO.orderDate( order.getOrderDate() );
        orderDTO.totalAmount( order.getTotalAmount() );
        orderDTO.products( productListToProductDTOList( order.getProducts() ) );

        return orderDTO.build();
    }

    private String orderUserUsername(Order order) {
        User user = order.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getUsername();
    }

    private String orderUserEmail(Order order) {
        User user = order.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getEmail();
    }

    protected List<ProductDTO> productListToProductDTOList(List<Product> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductDTO> list1 = new ArrayList<ProductDTO>( list.size() );
        for ( Product product : list ) {
            list1.add( productMapper.toDto( product ) );
        }

        return list1;
    }
}
