package sk.aconsulting.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sk.aconsulting.dto.ItemDto;
import sk.aconsulting.dto.ItemResponse;
import sk.aconsulting.models.Item;
import sk.aconsulting.repository.IItemRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTests {

    @Mock
    private IItemRepository iItemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    public void ItemService_CreateItem_ReturnsItemDto() {
        Item item = Item.builder()
                .name("Martin")
                .description("Dobry chlapec")
                .price(6D)
                .quantity(1).build();
        ItemDto itemDto = ItemDto.builder()
                .name("Martin")
                .description("Dobry chlapec")
                .price(6D)
                .quantity(1).build();
        when(iItemRepository.save(Mockito.any(Item.class))).thenReturn(item);

        ItemDto savedItem = itemService.addItem(itemDto);

        Assertions.assertThat(savedItem).isNotNull();
    }

    @Test
    public void ItemService_GetAllItem_ReturnsResponseDto() {
        Page<Item> items = Mockito.mock(Page.class);

        when(iItemRepository.findAll(Mockito.any(Pageable.class))).thenReturn(items);

        ItemResponse savePokemon = itemService.getAllItems(1,10);

        Assertions.assertThat(savePokemon).isNotNull();
    }

    @Test
    public void ItemService_FindById_ReturnItemDto() {
        Long itemId = 1L;
        Item item = Item.builder().id(1L).name("Martin")
                .description("Dobry chlapec")
                .price(6D)
                .quantity(1).build();

        when(iItemRepository.findById(itemId)).thenReturn(Optional.ofNullable(item));

        ItemDto itemReturn = itemService.getItemById(itemId);

        Assertions.assertThat(itemReturn).isNotNull();
    }

    @Test
    public void PokemonService_UpdatePokemon_ReturnPokemonDto() {
        Long itemId = 1L;
        Item item = Item.builder().id(1L).name("Martin")
                .description("Dobry chlapec")
                .price(6D)
                .quantity(1).build();

        ItemDto itemDto = ItemDto.builder().id(1L).name("Martin")
                .description("Dobry chlapec")
                .price(6D)
                .quantity(1).build();

        when(iItemRepository.findById(itemId)).thenReturn(Optional.ofNullable(item));
        when(iItemRepository.save(item)).thenReturn(item);

        ItemDto updateReturn = itemService.updateItem(itemId,itemDto);

        Assertions.assertThat(updateReturn).isNotNull();
    }

    @Test
    public void PokemonService_DeletePokemonById_ReturnVoid() {
        Long itemId = 1L;
        Item item = Item.builder().id(1L).name("Martin")
                .description("Dobry chlapec")
                .price(6D)
                .quantity(1).build();

        when(iItemRepository.findById(itemId)).thenReturn(Optional.ofNullable(item));
        doNothing().when(iItemRepository).delete(item);

        assertAll(() -> itemService.deleteItem(itemId));
    }

}
