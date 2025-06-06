/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemaventazamira.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sistemaventazamira.model.Usuario;

/**
 * FXML Controller class
 *
 * @author ilse_
 */
public class AdminController implements Initializable {

    @FXML
    private TableView<Usuario> tablaUsuarios;

    @FXML
    private TableColumn<Usuario, Number> columnaId;

    @FXML
    private TableColumn<Usuario, String> columnaNombre;

    @FXML
    private TextField campoId;

    @FXML
    private TextField campoNombre;

    @FXML
    private Button btnAgregar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnaId.setCellValueFactory(cellData -> cellData.getValue().idUsuarioProperty());
        columnaNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
    }

    @FXML
    private void agregarUsuario() {
        try {
            int id = Integer.parseInt(campoId.getText());
            String nombre = campoNombre.getText();

            if (!nombre.isEmpty()) {
                Usuario nuevoUsuario = new Usuario(id, nombre);
                tablaUsuarios.getItems().add(nuevoUsuario);

                campoId.clear();
                campoNombre.clear();
            }
        } catch (NumberFormatException e) {
            // Aquí podrías mostrar un error en la interfaz sobre el id inválido
            System.out.println("ID inválido, debe ser un número entero");
        }
    }
    
}
