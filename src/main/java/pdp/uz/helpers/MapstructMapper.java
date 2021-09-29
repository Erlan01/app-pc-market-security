package pdp.uz.helpers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import pdp.uz.domain.*;
import pdp.uz.model.*;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MapstructMapper {

    Category toCategory(CategoryDto dto);

    Category toCategory(Category category);

    Client toClient(ClientDto dto);

    Client toClient(Client client);

    Product toProduct(ProductDto dto);

    Order toOrder(OrderDto dto);
}
