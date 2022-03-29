package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;

public class ControllerMENU {
    @FXML
    private MenuItem QLSANPHAM;

    @FXML
    private MenuItem QLLOAI;

    @FXML
    private MenuItem QLNCC;

    @FXML
    private MenuItem QLKHO;

    @FXML
    private MenuItem QLKH;

    @FXML
    private Button btnhd;

    @FXML
    private Button btnbaotri;

    @FXML
    private Button btnnv;

    @FXML
    private Button btndx;

    @FXML
    private MenuItem TDT;

    @FXML
    private MenuItem DTNV;

    @FXML
    private MenuItem SPBC;

    @FXML
    private MenuItem SLTON;
    public void  QLSPOnAction (ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("SANPHAM.fxml"));
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
    public void  QLLOnAction (ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("LOAI.fxml"));
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
    public void  QLKOnAction (ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("KHO.fxml"));
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
    public void  QLNCCOnAction (ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("NHACUNGCAP.fxml"));
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
    public void  QLKHOnAction (ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("KHACHHANG.fxml"));
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
    public void  QLHDOnAction (ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("HOADON.fxml"));
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
    public void  btnbaotriOnAction (ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("PHIEUBAOTRI.fxml"));
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
    public void  btnnvOnAction (ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("NHANVIEN.fxml"));
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
    public void  btndxOnAction (ActionEvent event)
    {
        Alert VV =new Alert(Alert.AlertType.WARNING,"Bạn thật sự muốn thoát?", ButtonType.OK);
        VV.initStyle(StageStyle.UNDECORATED);
        VV.showAndWait();
        btndx.getScene().getWindow().hide();
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("DANGNHAP.fxml"));
            Stage NV =new Stage();
            NV.initStyle(StageStyle.UNDECORATED);
            NV.setScene(new Scene(root, 600, 400));
            NV.showAndWait();
        }catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }
    public void  TDTOnAction (ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("TONGDOANHTHU.fxml"));
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
    public void  DTNVOnAction (ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("DOANHTHUNHANVIEN.fxml"));
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
    public void SPBCOnAction (ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("SANPHAMBANCHAY.fxml"));
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
    public void  SLTONOnAction (ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("SLTON.fxml"));
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
}
