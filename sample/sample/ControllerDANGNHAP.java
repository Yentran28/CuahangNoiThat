package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Model.NHANVIEN;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ControllerDANGNHAP implements Initializable {
    private Connection con=null;
    private PreparedStatement pst=null;
    private ResultSet rs=null;

    @FXML
    private Button btndn;

    @FXML
    private Button btndk;

    @FXML
    private Button btnthoat;

    @FXML
    private TextField txttk;

    @FXML
    private PasswordField txtmk;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = sample.DBConnection.pmartConnection();

    }
    private void cleardata() {

        txtmk.clear();
        txttk.clear();

    }
    public void login(ActionEvent event) throws  Exception
    {
        if(txttk.getText().isEmpty()== false && txtmk.getText().isEmpty() == false){
            try{
                con = DBConnection.pmartConnection();

                pst = con.prepareStatement("Select * From NHANVIEN Where TAIKHOAN=? and MATKHAU=?");
                pst.setString(1,txttk.getText());
                pst.setString(2,txtmk.getText());
                ResultSet rs =pst.executeQuery();
                if(rs.next())
                {
                    Alert VV =new Alert(Alert.AlertType.NONE,"Đăng Nhập Thành Công", ButtonType.OK);
                    VV.initStyle(StageStyle.UNDECORATED);
                    VV.showAndWait();
                    btndn.getScene().getWindow().hide();
                    try
                    {
                        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                        FXMLLoader loader =new FXMLLoader();
                        loader.setLocation(getClass().getResource("MENU.fxml"));
                        Parent NV =loader.load();
                        Scene scene =new Scene(NV, 800, 700);
                        stage.setScene(scene);
                        stage.show();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        e.getCause();
                    }
                }else
                    JOptionPane.showMessageDialog(null,"Đăng Nhập Thất Bại");
                cleardata();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Tài khoản và mật khẩu không được để trống!");
        }
    }

    public void dangky(ActionEvent event) throws  Exception
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("DANGKY.fxml"));
            Stage NV =new Stage();
            NV.initStyle(StageStyle.UNDECORATED);
            NV.setScene(new Scene(root, 800, 700));
            NV.showAndWait();
        }catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }
    @FXML
    void handlecancel(ActionEvent event) {
        Stage stage = (Stage) btnthoat.getScene().getWindow();
        stage.close();
    }
}
