package sample.Controllerr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Model.CTPN;

import javafx.fxml.FXML;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerCTPN  implements Initializable {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<CTPN> data;
    @FXML
    private TextField txtpn;

    @FXML
    private TextField txtsp;

    @FXML
    private Button btnthem;

    @FXML
    private Button btnxoa;

    @FXML
    private Button btnthoat;

    @FXML
    private TextField txtsl;

    @FXML
    private TableView<CTPN> tablectpn;

    @FXML
    private TableColumn<?, ?> colpn;

    @FXML
    private TableColumn<?, ?> colsp;

    @FXML
    private TableColumn<?, ?> colsl;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = sample.DBConnection.pmartConnection();
        data = FXCollections.observableArrayList();
        setCellTable();
        LoadDataTableView();
        setcellvalueformtableview();
        //SearchNV();
    }
    private void setCellTable() {
        colpn.setCellValueFactory(new PropertyValueFactory<>("MAPN"));
        colsp.setCellValueFactory(new PropertyValueFactory<>("MASP"));
        colsl.setCellValueFactory(new PropertyValueFactory<>("SLNHAP"));



    }
    private void cleardata() {
        txtpn.clear();
        txtsl.clear();
        txtsp.clear();
    }
    private void LoadDataTableView() {
        data.clear();
        try {
            pst = con.prepareStatement("SELECT MAPN,MASP,SLNHAP FROM CTPN");
            rs = pst.executeQuery();
            while (rs.next()) {

                data.add(new CTPN(rs.getString(1), rs.getString(2), rs.getString(3)));

            }

        } catch (SQLException ex) {
            Logger.getLogger(ControllerCTPN.class.getName()).log(Level.SEVERE, null, ex);
        }
        tablectpn.setItems(data);
    }

    private void setcellvalueformtableview(){
        tablectpn.setOnMouseClicked(e->{
            CTPN p1 = tablectpn.getItems().get(tablectpn.getSelectionModel().getFocusedIndex());
            txtpn.setText(p1.getMAPN());
            txtsp.setText(p1.getMASP());
            txtsl.setText(p1.getSLNHAP());
        });
    }
    @FXML
    private void handleAdd(ActionEvent event) throws SQLException {
        if(txtpn.getText().isEmpty()== false && txtsp.getText().isEmpty() == false&& txtsl.getText().isEmpty() == false){
            if(checkPhone(txtsl.getText())==true){
        String sql = "Insert Into CTPN(MAPN,MASP,SLNHAP) Values(?,?,?)";
        String pn = txtpn.getText();
        String sp = txtsp.getText();
        String sl = txtsl.getText();

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, pn);
            pst.setString(2, sp);
            pst.setString(3, sl);

            int i = pst.executeUpdate();
            if (i == 1) {
                System.out.println("Data Insert Successfully");
                LoadDataTableView();
                Alert VV = new Alert(Alert.AlertType.WARNING, "Thêm Thành Công", ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
                cleardata();
            }
        } catch (SQLException ex) {
            Alert VV = new Alert(Alert.AlertType.WARNING, " Lỗi Nhập Liệu", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
            cleardata();
            Logger.getLogger(ControllerCTPN.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pst.close();
        }}else{
                Alert VV = new Alert(Alert.AlertType.WARNING, " Số điện thoại không hợp lệ!", ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
            }
        }else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Cần điền đầy đủ thông tin!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }
    }
    public void handleDelete(ActionEvent event) throws SQLException {
        String sql = "delete from CTPN where MAPN = ? and MASP = ? and SLNHAP = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,txtpn.getText());
            pst.setString(2,txtsp.getText());
            pst.setString(3,txtsl.getText());

            int i=  pst.executeUpdate();
            if(i==1 || i==2){
                System.out.println("Data Delete Successfully");
                LoadDataTableView();
                Alert VV =new Alert(Alert.AlertType.WARNING,"Xóa Thành Công",ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
                cleardata();

            }
        }catch(SQLException ex){
            Alert VV =new Alert(Alert.AlertType.WARNING," Lỗi Nhập Liệu",ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
            cleardata();
            Logger.getLogger(ControllerCTPN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void CancelButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnthoat.getScene().getWindow();
        stage.close();
    }
            public static boolean checkPhone(String value) {

                try {
                    Pattern pattern = Pattern.compile("^[0-9]*$");
                    Matcher matcher = pattern.matcher(value);
                    return matcher.matches();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }
}
