package sk.aconsulting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sk.aconsulting.dto.ItemDto;
import sk.aconsulting.dto.ItemResponse;
import sk.aconsulting.models.Item;
import sk.aconsulting.service.ItemService;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ItemController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Autowired
    private ObjectMapper objectMapper;
    private Item item;
    private ItemDto itemDto;

    @BeforeEach
    public void init() {
        item = Item.builder()
                .name("Martin")
                .description("Dobry chlapec")
                .price(6D)
                .quantity(1).build();

        itemDto = ItemDto.builder().name("Martin")
                .description("Dobry chlapec")
                .price(6D)
                .quantity(1).build();
    }

    @Test
    public void ItemController_AddItem_ReturnAdded() throws Exception {
        given(itemService.addItem(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(itemDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(itemDto.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", CoreMatchers.is(itemDto.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity", CoreMatchers.is(itemDto.getQuantity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt", CoreMatchers.is(itemDto.getCreatedAt())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt", CoreMatchers.is(itemDto.getUpdatedAt())));

    }

    @Test
    public void ItemController_GetAllItems_ReturnResponseDto() throws Exception {
        ItemResponse responseDto = ItemResponse.builder().pageSize(10).last(true).pageNo(1).content(Arrays.asList(itemDto)).build();
        when(itemService.getAllItems(1,10)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo","1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())));
    }

    @Test
    public void ItemController_AddItemById_ReturnItemDto() throws Exception {
        Long itemId = 1L;
        when(itemService.getItemById(itemId)).thenReturn(itemDto);

        ResultActions response = mockMvc.perform(get("/api/items/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(itemDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(itemDto.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", CoreMatchers.is(itemDto.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity", CoreMatchers.is(itemDto.getQuantity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt", CoreMatchers.is(itemDto.getCreatedAt())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt", CoreMatchers.is(itemDto.getUpdatedAt())));
    }

    @Test
    public void ItemController_UpdateItem_ReturnItemDto() throws Exception {
        Long itemId = 1L;
        when(itemService.updateItem(itemId, itemDto)).thenReturn(itemDto);

        ResultActions response = mockMvc.perform(put("/api/items/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(itemDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(itemDto.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", CoreMatchers.is(itemDto.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity", CoreMatchers.is(itemDto.getQuantity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt", CoreMatchers.is(itemDto.getCreatedAt())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt", CoreMatchers.is(itemDto.getUpdatedAt())));
    }

    @Test
    public void ItemController_DeleteItem_ReturnString() throws Exception {
        doNothing().when(itemService).deleteItem(1L);

        ResultActions response = mockMvc.perform(delete("/api/items/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}