package com.thyago.bookshelf_api.config;

import com.thyago.bookshelf_api.dto.LivroResponseDTO;
import com.thyago.bookshelf_api.entity.Livro;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Livro.class, LivroResponseDTO.class)
                .setPostConverter(context -> {
                    Livro source = context.getSource();
                    LivroResponseDTO dest = context.getDestination();
                    if (source.getCategoria() != null) {
                        dest.setNomeCategoria(source.getCategoria().getNome());
                    }
                    return dest;
                });

        return modelMapper;
    }
}