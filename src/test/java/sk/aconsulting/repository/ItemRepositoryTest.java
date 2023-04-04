package sk.aconsulting.repository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sk.aconsulting.models.Item;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ItemRepositoryTest {

    @Autowired
    private IItemRepository iItemRepository;

    @Test
    public void Repository_SaveAll_ReturnSavedItem() {

        //Arrange
        Item item = Item.builder()
                .name("Martin")
                .description("Dobry chlapec")
                .price(6D)
                .quantity(1).build();

        //Act
        Item savedItem = iItemRepository.save(item);

        //Assert
        Assertions.assertThat(savedItem).isNotNull();
        Assertions.assertThat(savedItem.getId()).isGreaterThan(0);
    }

    @Test
    public void ItemRepository_FindById_ReturnItem() {
        Item item = Item.builder()
                .name("Martin")
                .description("Dobry chlapec")
                .price(6D)
                .quantity(1).build();

        iItemRepository.save(item);

        Item itemList = iItemRepository.findById(item.getId()).get();

        Assertions.assertThat(itemList).isNotNull();
    }

    @Test
    public void ItemRepository_GetAll_ReturnMoreThenOneItem() {
        Item item = Item.builder()
                .name("Martin")
                .description("Dobry chlapec")
                .price(6D)
                .quantity(1).build();
        Item item2 = Item.builder()
                .name("Martin")
                .description("Dobry chlapec")
                .price(6D)
                .quantity(1).build();

        iItemRepository.save(item);
        iItemRepository.save(item2);

        List<Item> pokemonList = iItemRepository.findAll();

        Assertions.assertThat(pokemonList).isNotNull();
        Assertions.assertThat(pokemonList.size()).isEqualTo(2);
    }

    @Test
    public void ItemRepository_UpdatePokemon_ReturnPokemonNotNull() {
        Item item = Item.builder()
                .name("Martin")
                .description("Dobry chlapec")
                .price(6D)
                .quantity(1).build();

        iItemRepository.save(item);

        Item itemSave = iItemRepository.findById(item.getId()).get();

        itemSave.setName("Raichu");

        itemSave.setName("Martin");
        itemSave.setDescription("Dobry chlapec");
        itemSave.setPrice(6D);
        itemSave.setQuantity(1);
        itemSave.setCreatedAt(LocalDateTime.now());
        itemSave.setUpdatedAt(LocalDateTime.now());

        Item updatedPokemon = iItemRepository.save(itemSave);

        Assertions.assertThat(updatedPokemon.getName()).isNotNull();
        Assertions.assertThat(updatedPokemon.getDescription()).isNotNull();
        Assertions.assertThat(updatedPokemon.getPrice()).isNotNull();
        Assertions.assertThat(updatedPokemon.getQuantity()).isNotNull();
        Assertions.assertThat(updatedPokemon.getCreatedAt()).isNotNull();
        Assertions.assertThat(updatedPokemon.getUpdatedAt()).isNotNull();
    }

    @Test
    public void PokemonRepository_PokemonDelete_ReturnPokemonIsEmpty() {
        Item item = Item.builder()
                .name("Martin")
                .description("Dobry chlapec")
                .price(6D)
                .quantity(1).build();

        iItemRepository.save(item);

        iItemRepository.deleteById(item.getId());
        Optional<Item> itemReturn = iItemRepository.findById(item.getId());

        Assertions.assertThat(itemReturn).isEmpty();
    }

}
