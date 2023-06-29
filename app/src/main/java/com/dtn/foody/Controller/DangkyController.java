package com.dtn.foody.Controller;

import com.dtn.foody.Model.ThanhVienModel;

public class DangkyController {
    ThanhVienModel thanhVienModel;
    public DangkyController()
    {
        thanhVienModel = new ThanhVienModel();
    }
    public void themThanhVienController(ThanhVienModel thanhVienModel, String uid)
    {
        thanhVienModel.themThanhvienController(thanhVienModel, uid);
    }
}
