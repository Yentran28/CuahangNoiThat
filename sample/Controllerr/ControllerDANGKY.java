package sample.Controllerr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Model.NHANVIEN;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerDANGKY implements Initializable {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<NHANVIEN> data;


    @FXML
    private TextField txtma;

    @FXML
    private TextField txtten;

    @FXML
    private TextField txttk;

    @FXML
    private TextField txtmk;

    @FXML
    private TextField txtsdt;

    @FXML
    private TextField txtdiachi;

    @FXML
    private TextField txtemail;

    @FXML
    private Button btnthem;

    @FXML
    private Button btnthoat;

    @FXML
    private void handleAddNhanVien(ActionEvent event) throws SQLException {
        if(txtma.getText().isEmpty()== false && txtten.getText().isEmpty() == false&& txttk.getText().isEmpty() == false&& txtmk.getText().isEmpty() == false&& txtsdt.getText().isEmpty() == false&& txtdiachi.getText().isEmpty() == false&& txtemail.getText().isEmpty() == false){
            String sql = "Insert Into NHANVIEN(MANV,TENNV,TAIKHOAN,MATKHAU,SĐT,DIACHI,EMAIL) Values(?,?,?,?,?,?,?)";
            String ma = txtma.getText();
            String ten = txtten.getText();
            String tk = txttk.getText();
            String mk = txtmk.getText();
            String sdt = txtsdt.getText();
            String diachi = txtdiachi.getText();
            String email = txtemail.getText();

            try {
                pst = con.prepareStatement(sql);
                pst.setString(1, ma);
                pst.setString(2, ten);
                pst.setString(3, tk);
                pst.setString(4, mk);
                pst.setString(5, sdt);
                pst.setString(6, diachi);
                pst.setString(7, email);

                int i = pst.executeUpdate();
                if (i == 1) {
                    System.out.println("Data Insert Successfully");
                    Alert VV =new Alert(Alert.AlertType.WARNING,"Đăng ký thành công!", ButtonType.OK);
                    VV.initStyle(StageStyle.UNDECORATED);
                    VV.showAndWait();
                    //btnthem.getScene().getWindow().hide();
//                    try
//                    {
//                        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
//                        FXMLLoader loader =new FXMLLoader();
//                        loader.setLocation(getClass().getResource("DANGNHAP.fxml"));
//                        Parent NV =loader.load();
//                        Scene scene =new Scene(NV, 800, 700);
//                        stage.setScene(scene);
//                        stage.show();
//                    }catch (Exception e)
//                    {
//                        e.printStackTrace();
//                        e.getCause();
//                    }
                }
            } catch (SQLException ex) {
                Alert VV =new Alert(Alert.AlertType.WARNING," Lỗi Nhập Liệu",ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
                Logger.getLogger(ControllerNHANVIEN.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                pst.close();
            }}else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Cần điền đầy đủ thông tin!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }
    }
    public void CancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) btnthoat.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = sample.DBConnection.pmartConnection();
        data = FXCollections.observableArrayList();
    }
}
