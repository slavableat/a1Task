package com.example.demo.service;

import com.example.demo.dto.ItemDTO;
import com.example.demo.dto.PostingDTO;
import com.example.demo.dto.PostingMapper;
import com.example.demo.model.Posting;
import com.example.demo.repository.PostingRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.demo.service.PostingsService.POSTINGS_OUT_FILE_PATH;

@Service
public class PostingService {
    private final PostingRepository postingRepository;
    private final PostingMapper postingMapper;
    Map<Long, PostingDTO> postings = new HashMap<>();
    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d.MM.uuuu");

    @Autowired
    public PostingService(PostingRepository postingRepository, PostingMapper postingMapper) {
        this.postingRepository = postingRepository;
        this.postingMapper = postingMapper;
    }

    public List<PostingDTO> getAll() {
        List<Posting> postings = postingRepository.findAll();
        return postings.stream()
                .map(postingMapper::postingDtoFromPosting)
                .collect(Collectors.toList());
    }

    public List<PostingDTO> getAllBetween(String startDate, String endDate, String authorized) {
        LocalDate start = LocalDate.parse(startDate, formatters);
        LocalDate end = LocalDate.parse(endDate, formatters);
        if(authorized == null){
            List<Posting> postings = postingRepository.findByPostingDateBetween(start, end);
            return postings.stream()
                    .map(postingMapper::postingDtoFromPosting)
                    .collect(Collectors.toList());
        }else{
            Boolean isAuthorized = Boolean.parseBoolean(authorized);
            List<Posting> postings = postingRepository.findByPostingDateBetweenAndIsAuthorized(start, end, isAuthorized);
            return postings.stream()
                    .map(postingMapper::postingDtoFromPosting)
                    .collect(Collectors.toList());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //TODO initial state of postings
    public void initTable() {
        List<PostingDTO> postingDTOs = getPostingDTOs();
        postingDTOs.forEach(postingDTO ->
                postingRepository.save(postingMapper.postingFromDTO(postingDTO))
        );
    }

    private List<PostingDTO> getPostingDTOs() {
        parsePostingsFile();
        return new ArrayList<>(this.postings.values());
    }

    private void parseLine(String[] line) {
        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setMaterialDescription(line[4]);
        itemDTO.setQuantity(Long.parseLong(line[5]));
        itemDTO.setBUn(line[6]);
        itemDTO.setCrcy(line[8]);
        itemDTO.setAmount(Double.parseDouble(line[7].replaceAll(",", ".")));
        itemDTO.setOrdinalNumber(Long.parseLong(line[1]));

        if (this.postings.get(Long.parseLong(line[0])) != null) {
            this.postings.get(Long.parseLong(line[0])).getItems().add(itemDTO);
        } else {
            PostingDTO postingDTO = new PostingDTO();

            postingDTO.setMatDoc(Long.parseLong(line[0]));
            postingDTO.setIsAuthorized(Boolean.parseBoolean(line[10]));
            postingDTO.setAccountName(line[9]);
            postingDTO.setDocDate(line[2]);
            postingDTO.setPostingDate(line[3]);
            postingDTO.getItems().add(itemDTO);

            this.postings.put(postingDTO.getMatDoc(), postingDTO);
        }
    }

    private void parsePostingsFile() {
        List<String[]> lines = new ArrayList<>();
        try (CSVReader fileReader = new CSVReader(new FileReader(POSTINGS_OUT_FILE_PATH), '\t', CSVWriter.NO_QUOTE_CHARACTER, ';')) {
            String[] line;
            fileReader.readNext();
            while ((line = fileReader.readNext()) != null) {
                if (line[0].equals("")) {
                    continue;
                }
                lines.add(line);
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lines.forEach(this::parseLine);
    }
}
