package com.marmitech.Marmitech.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marmitech.Marmitech.DTO.RequestDTO.HistoricoSaveDTO;
import com.marmitech.Marmitech.DTO.ResponseDTO.HistoricoResponseDTO;
import com.marmitech.Marmitech.Entity.HistoricoCompra;
import com.marmitech.Marmitech.Services.HistoricoCompraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HistoricoCompraController.class)
public class HistoricoCompraControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    HistoricoCompraService historicoCompraService;

    @Autowired
    ObjectMapper objectMapper;

    HistoricoCompra historicoCompra;
    HistoricoSaveDTO historicoSaveDTO;
    HistoricoResponseDTO historicoResponseDTO;

    @BeforeEach
    void setUp() {
        historicoCompra = new HistoricoCompra();
        historicoCompra.setId( 1 );
        historicoCompra.setDataEvento( "2025-10-12" );
        historicoCompra.setTipoEvento( "CRIADO" );
        historicoCompra.setDescricao( "Pedido Criado" );

        // DTO de request para o POST
        historicoSaveDTO = new HistoricoSaveDTO(
                0, // pedidoId (0 quando nao enviado pelo teste de controller)
                historicoCompra.getDescricao(),
                historicoCompra.getDataEvento(),
                historicoCompra.getTipoEvento()
        );

        // DTO de response que o service/controller retorna nas buscas
        historicoResponseDTO = new HistoricoResponseDTO(
                historicoCompra.getId(),
                historicoCompra.getTipoEvento(),
                historicoCompra.getDataEvento(),
                0
        );
    }

    @Test
    @DisplayName("POST /save - Deve salvar um HistoricoCompra com status CREATED")
    void cenario01() throws Exception {
        Mockito.when( historicoCompraService.save( any( HistoricoSaveDTO.class ) ) ).thenReturn( historicoCompra );

        String historicoJson = objectMapper.writeValueAsString( historicoSaveDTO );

        mockMvc.perform( post( "/historicoCompra/save" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( historicoJson ) )
                .andDo( print() )
                .andExpect( status().isCreated() )
                .andExpect( jsonPath( "$.id" ).value( 1 ) )
                .andExpect( jsonPath( "$.tipoEvento" ).value( "CRIADO" ) );
    }

    @Test
    @DisplayName("POST /save - Deve retornar BAD_REQUEST em caso de falha no serviço")
    void cenario02() throws Exception {
        Mockito.when( historicoCompraService.save( any( HistoricoSaveDTO.class ) ) ).thenThrow( new RuntimeException( "Erro de teste" ) );

        String historicoJson = objectMapper.writeValueAsString( historicoSaveDTO );

        mockMvc.perform( post( "/historicoCompra/save" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( historicoJson ) )
                .andDo( print() )
                .andExpect( status().isBadRequest() );
    }

    @Test
    @DisplayName("PUT /update/{id} - Deve atualizar um HistoricoCompra com status OK")
    void cenario03() throws Exception {

        HistoricoCompra historicoAtualizado = new HistoricoCompra();
        historicoAtualizado.setId( historicoCompra.getId() );
        historicoAtualizado.setDataEvento( historicoCompra.getDataEvento() );
        historicoAtualizado.setTipoEvento( historicoCompra.getTipoEvento() );
        historicoAtualizado.setDescricao( "Descrição Atualizada" );

        Mockito.when( historicoCompraService.update( any( HistoricoCompra.class ), anyInt() ) ).thenReturn( historicoAtualizado );

        String historicoJson = objectMapper.writeValueAsString( historicoAtualizado );

        mockMvc.perform( put( "/historicoCompra/update/1" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( historicoJson ) )
                .andDo( print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.descricao" ).value( "Descrição Atualizada" ) );
    }

    @Test
    @DisplayName("DELETE /delete/{id} - Deve deletar um HistoricoCompra com status OK")
    void cenario04() throws Exception {
        Mockito.when( historicoCompraService.delete( 1 ) ).thenReturn( "Historico deletado com sucesso!" );

        mockMvc.perform( delete( "/historicoCompra/delete/1" ) )
                .andDo( print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$" ).value( "Historico deletado com sucesso!" ) );
    }

    @Test
    @DisplayName("DELETE /delete/{id} - Deve retornar BAD_REQUEST em caso de falha")
    void cenario05() throws Exception {
        final String expectedErrorMessage = "Falha ao tentar deletar o histórico.";

        Mockito.when( historicoCompraService.delete( 1 ) )
                .thenThrow( new RuntimeException( expectedErrorMessage ) );

        mockMvc.perform( delete( "/historicoCompra/delete/1" ) )

                .andExpect( status().isBadRequest() )

                .andExpect( content().string( expectedErrorMessage ) );

        Mockito.verify( historicoCompraService, Mockito.times( 1 ) ).delete( 1 );
    }

    @Test
    @DisplayName("GET /findById/{id} - Deve retornar um HistoricoCompra por ID com status OK")
    void cenario06() throws Exception {
        Mockito.when( historicoCompraService.findById( 1 ) ).thenReturn( historicoResponseDTO );

        mockMvc.perform( get( "/historicoCompra/findById/1" ) )
                .andDo( print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.id" ).value( 1 ) );
    }

    @Test
    @DisplayName("GET /findById/{id} - Deve retornar BAD_REQUEST se não encontrar o ID")
    void cenario07() throws Exception {
        Mockito.when( historicoCompraService.findById( 99 ) ).thenThrow( new NoSuchElementException() );

        mockMvc.perform( get( "/historicoCompra/findById/99" ) )
                .andDo( print() )
                .andExpect( status().isBadRequest() );
    }

    @Test
    @DisplayName("GET / - Deve retornar todos os HistoricoCompra com status OK")
    void cenario08() throws Exception {

        Mockito.when( historicoCompraService.findAll() ).thenReturn( List.of( historicoResponseDTO ) );

        mockMvc.perform( get( "/historicoCompra" ) )
                .andDo( print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$[0].id" ).value( 1 ) );
    }

    @Test
    @DisplayName("GET /findByDataEvento - Deve retornar HistoricoCompra por DataEvento com status OK")
    void cenario09() throws Exception {
        Mockito.when( historicoCompraService.findByDataEvento( "2025-10-12" ) ).thenReturn( List.of( historicoResponseDTO ) );

        mockMvc.perform( get( "/historicoCompra/findByDataEvento" )
                        .param( "dataEvento", "2025-10-12" ) )
                .andDo( print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$[0].dataEvento" ).value( "2025-10-12" ) );
    }
}