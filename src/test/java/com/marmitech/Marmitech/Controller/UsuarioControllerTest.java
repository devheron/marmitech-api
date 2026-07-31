// package com.marmitech.Marmitech.Controller;

// import java.time.LocalDate;
// import java.util.List;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyInt;
// import static org.mockito.ArgumentMatchers.anyString;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.context.bean.override.mockito.MockitoBean;
// import org.springframework.test.web.servlet.MockMvc;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.marmitech.Marmitech.Entity.Usuario;
// import com.marmitech.Marmitech.Services.UsuarioService;

// @WebMvcTest(UsuarioController.class)
// @ActiveProfiles("test")
// class UsuarioControllerTest {

//     @Autowired
//     MockMvc mockMvc;

//     @MockitoBean
//     private UsuarioService usuarioService;

//     @Autowired
//     private ObjectMapper objectMapper;

//     Usuario usuario;

//     @BeforeEach
//     void setUp() {
//         usuario = new Usuario();
//         usuario.setId(1);
//         usuario.setNome("Marmitech");
//         usuario.setSenha("123456");
//         usuario.setEmail("marmitech@gmail.com");
//         usuario.setCargo("Caixa");
//         usuario.setData_criacao(LocalDate.now());
//     }

//     @Test
//     @DisplayName("01 - POST /save - Deve criar um novo usuario com sucesso")
//     void cenario01() throws Exception {
//         // Simula o comportamento do service
//         Mockito.when(usuarioService.save(any(Usuario.class)))
//                 .thenReturn(usuario);

//         // Transforma o objeto em JSON
//         String usuarioJson = objectMapper.writeValueAsString(usuario);

//         // Executa o POST simulando a requisição HTTP
//         mockMvc.perform(post("/api/usuario/save")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(usuarioJson))
//                 .andDo(print())
//                 .andExpect(status().isCreated())
//                 .andExpect(jsonPath("$.id").value(1))
//                 .andExpect(jsonPath("$.nome").value("Marmitech"))
//                 .andExpect(jsonPath("$.senha").value("123456"))
//                 .andExpect(jsonPath("$.email").value("marmitech@gmail.com"))
//                 .andExpect(jsonPath("$.cargo").value("Caixa"))
//                 .andExpect(jsonPath("$.data_criacao").value(LocalDate.now().toString()));
//     }

//     @Test
//     @DisplayName("02 - GET /findAll - Deve retornar a lista de usuario cadastrados")
//     void cenario02() throws Exception {
//         // Simula o retorno do service
//         Mockito.when(usuarioService.findAll())
//                 .thenReturn(List.of(usuario));

//         // Executa o GET simulando a requisição HTTP
//         mockMvc.perform(get("/api/usuario/findAll"))
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$[0].id").value(1))
//                 .andExpect(jsonPath("$[0].nome").value("Marmitech"))
//                 .andExpect(jsonPath("$[0].senha").value("123456"))
//                 .andExpect(jsonPath("$[0].email").value("marmitech@gmail.com"))
//                 .andExpect(jsonPath("$[0].cargo").value("Caixa"))
//                 .andExpect(jsonPath("$[0].data_criacao").value(LocalDate.now().toString()));
//     }

//     @Test
//     @DisplayName("03 - GET /findById - Buscar usuário pelo ID")
//     void cenario03() throws Exception {
//         // Simula o retorno do service
//         Mockito.when(usuarioService.findById(anyInt()))
//                 .thenReturn(usuario);

//         // Executa o GET simulando a requisição HTTP
//         mockMvc.perform(get("/api/usuario/findById/{id}", 1))
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").value(1))
//                 .andExpect(jsonPath("$.nome").value("Marmitech"))
//                 .andExpect(jsonPath("$.senha").value("123456"))
//                 .andExpect(jsonPath("$.email").value("marmitech@gmail.com"))
//                 .andExpect(jsonPath("$.cargo").value("Caixa"))
//                 .andExpect(jsonPath("$.data_criacao").value(LocalDate.now().toString()));
//     }

//     @Test
//     @DisplayName("04-Cenario UP Deve atualizar os dados de um um usuario existente")
//     void cenario04() throws Exception {
//         // Simula o retorno do service
//         Mockito.when(usuarioService.update(anyInt(), any(Usuario.class))).thenReturn(usuario);

//         String usuarioJson = objectMapper.writeValueAsString(usuario);

//         mockMvc.perform(put("/api/usuario/update/{id}", 1)
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(usuarioJson))
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").value(1))
//                 .andExpect(jsonPath("$.nome").value("Marmitech"))
//                 .andExpect(jsonPath("$.email").value("marmitech@gmail.com"))
//                 .andExpect(jsonPath("$.cargo").value("Caixa"))
//                 .andExpect(jsonPath("$.data_criacao").value(LocalDate.now().toString()));
//     }

//     @Test
//     @DisplayName("05 - Cenario Delete - Deve excluir um usuário com sucesso")
//     void cenario05() throws Exception {
//         Mockito.doNothing().when(usuarioService).delete(anyInt());

//         mockMvc.perform(delete("/api/usuario/delete/{id}", 1))
//                 .andDo(print())
//                 .andExpect(status().isOk());
//     }

//     @Test
//     @DisplayName("06 - Cenario  Deve fazer login com sucesso")
//     void cenario06() throws Exception {
//        Mockito.doNothing().when(usuarioService).login(any(),any());
//        mockMvc.perform(post("/api/usuario/login").param("nome","marmitech")
//        .param("senha","123456")).andDo(print())
//                .andExpect(status().isAccepted());

//     }
//     @Test
//     @DisplayName("07 - Cenario  Deve buscar usuários pelo cargo")
//     void cenario07() throws Exception {
//         Mockito.when(usuarioService.findByCargo(anyString())).thenReturn(List.of(usuario));
//         mockMvc.perform(get("/api/usuario/findByCargo/{cargo}", "Caixa"))
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$[0].id").value(1))
//                 .andExpect(jsonPath("$[0].nome").value("Marmitech"))
//                 .andExpect(jsonPath("$[0].senha").value("123456"))
//                 .andExpect(jsonPath("$[0].email").value("marmitech@gmail.com"))
//                 .andExpect(jsonPath("$[0].cargo").value("Caixa"))
//                 .andExpect(jsonPath("$[0].data_criacao").value(LocalDate.now().toString()));
//     }
//     @Test
//     @DisplayName("08 - Cenario ")
//     void cenario08() throws Exception {
//         Mockito.when( usuarioService.findByNome(anyString())).thenReturn(List.of(usuario));
//         mockMvc.perform(get("/api/usuario/findByNome/{nome}", "Marmitech"))
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$[0].id").value(1))
//                 .andExpect(jsonPath("$[0].nome").value("Marmitech"))
//                 .andExpect(jsonPath("$[0].senha").value("123456"))
//                 .andExpect(jsonPath("$[0].email").value("marmitech@gmail.com"))
//                 .andExpect(jsonPath("$[0].cargo").value("Caixa"))
//                 .andExpect(jsonPath("$[0].data_criacao").value(LocalDate.now().toString()));
//     }
// }