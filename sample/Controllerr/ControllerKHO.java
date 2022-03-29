package sample.Controllerr;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Model.KHO;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControllerKHO implements Initializable {

    @FXML
    private TextField txtma;

    @FXML
    private TextField txtten;

    @FXML
    private Button btnthem;

    @FXML
    private Button btnxoa;

    @FXML
    private Button btnsua;

    @FXML
    private Button btnthoat;

    @FXML
    private TableView<KHO> tablekho;

    @FXML
    private TableColumn<?, ?> colma;

    @FXML
    private TableColumn<?, ?> colten;

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<KHO> data;

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
        colma.setCellValueFactory(new PropertyValueFactory<>("MAKHO"));
        colten.setCellValueFactory(new PropertyValueFactory<>("TENKHO"));

    }
    private void cleardata() {
        txtma.clear();
        txtten.clear();

    }
    private void LoadDataTableView() {
        data.clear();
        try {
            pst = con.prepareStatement("SELECT * FROM KHO");
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new KHO(rs.getString(1), rs.getString(2)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ControllerKHO.class.getName()).log(Level.SEVERE, null, ex);
        }
        tablekho.setItems(data);
    }

    private void setcellvalueformtableview()    {
        tablekho.setOnMouseClicked(e->{
            KHO p1 = tablekho.getItems().get(tablekho.getSelectionModel().getFocusedIndex());
            txtma.setText(p1.getMAKHO());
            txtten.setText(p1.getTENKHO());
        });
    }

    @FXML
    private void handleAdd(ActionEvent event) throws SQLException {
        String sql = "Insert Into KHO(MAKHO,TENKHO) Values(?,?)";
        String ma = txtma.getText();
        String ten = txtten.getText();

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, ma);
            pst.setString(2, ten);

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
            Logger.getLogger(ControllerKHO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            pst.close();
        }
    }

    public void handleDelete(ActionEvent event) throws SQLException {
        String sql = "delete from KHO where MAKHO = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,txtma.getText());
            int i=  pst.executeUpdate();
            if(i==1){
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
            Logger.getLogger(ControllerKHO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleUpdate(ActionEvent event) {
        String sql = "Update KHO  set  TENKHO=? WHERE MAKHO = ? ";
        try {
            String ma = txtma.getText();
            String ten = txtten.getText();

            pst = con.prepareStatement(sql);
            pst.setString(2, ma);
            pst.setString(1, ten);



            int i = pst.executeUpdate();
            if (i == 1) {
                System.out.println("Data Insert Successfully");
                LoadDataTableView();
                Alert VV =new Alert(Alert.AlertType.WARNING,"Sửa Thành Công",ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
                cleardata();

            }
        } catch (SQLException ex) {
            Alert VV =new Alert(Alert.AlertType.WARNING," Lỗi Nhập Liệu",ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
            cleardata();
            Logger.getLogger(ControllerKHO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void CancelButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnthoat.getScene().getWindow();
        stage.close();
    }

}
