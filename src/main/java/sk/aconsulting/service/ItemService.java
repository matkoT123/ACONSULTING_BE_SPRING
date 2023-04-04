package sk.aconsulting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sk.aconsulting.dto.ItemDto;
import sk.aconsulting.dto.ItemResponse;
import sk.aconsulting.repository.IItemRepository;
import sk.aconsulting.models.Item;
import sk.aconsulting.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService implements IItemService {

    @Autowired
    private IItemRepository repository;

    @Autowired
    public ItemService(IItemRepository repository) {
//        this.repository = repository;
//        Item item1 = new Item();
//        item1.setName("energetak");
//        item1.setDescription("doda energiu");
//        item1.setPrice(3.5);
//        item1.setQuantity(5);
//        item1.setCreatedAt(LocalDateTime.now());
//        this.repository.save(item1);
//
//        Item item2 = new Item();
//        item2.setName("mineralka");
//        item2.setDescription("hydratuje");
//        item2.setPrice(1.2);
//        item2.setQuantity(12);
//        item2.setCreatedAt(LocalDateTime.now());
//        this.repository.save(item2);
    }

    @Override
    public ItemDto addItem(ItemDto itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setPrice(itemDto.getPrice());
        item.setQuantity(itemDto.getQuantity());
        item.setCreatedAt(LocalDateTime.now());

        Item newItem = repository.save(item);

        ItemDto itemResponse = new ItemDto();
        itemResponse.setId(newItem.getId());
        itemResponse.setName(newItem.getName());
        itemResponse.setDescription(newItem.getDescription());
        itemResponse.setPrice(newItem.getPrice());
        itemResponse.setQuantity(newItem.getQuantity());
        itemResponse.setCreatedAt(LocalDateTime.now());

        return itemResponse;
    }

    @Override
    public ItemDto getItemById(Long id) {
        Item item = repository.findById(id).orElseThrow(() -> new NotFoundException("No such id"));
        return mapToDto(item);
    }


    @Override
    public ItemResponse getAllItems(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Item> items = repository.findAll(pageable);
        List<Item> itemList = items.getContent();
        List<ItemDto> content = itemList.stream().map(this::mapToDto).collect(Collectors.toList());

        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setContent(content);
        itemResponse.setPageNo(items.getNumber());
        itemResponse.setPageSize(items.getSize());
        itemResponse.setTotalElements(items.getTotalElements());
        itemResponse.setTotalPages(items.getTotalPages());
        itemResponse.setLast(items.isLast());

        return itemResponse;
    }

    @Override
    public ItemDto updateItem(Long id, ItemDto itemDto) {
        Item item = repository.findById(id).orElseThrow(() -> new NotFoundException("No such id"));
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setPrice(itemDto.getPrice());
        item.setQuantity(itemDto.getQuantity());
        item.setCreatedAt(itemDto.getCreatedAt());
        item.setUpdatedAt(LocalDateTime.now());

        Item updateditem = repository.save(item);
        return mapToDto(updateditem);

    }

    private ItemDto mapToDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setPrice(item.getPrice());
        itemDto.setQuantity(item.getQuantity());
        itemDto.setCreatedAt(item.getCreatedAt());
        itemDto.setUpdatedAt(item.getUpdatedAt());
        return itemDto;
    }

    private Item mapToEntity(ItemDto itemDto) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setPrice(itemDto.getPrice());
        item.setQuantity(itemDto.getQuantity());
        item.setCreatedAt(itemDto.getCreatedAt());
        item.setUpdatedAt(itemDto.getUpdatedAt());
        return item;
    }

    @Override
    public void deleteItem(Long id) {
        Item item = repository.findById(id).orElseThrow(() -> new NotFoundException("No such id"));
        repository.delete(item);
    }

}
