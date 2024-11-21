import { Router } from "express";
const router = Router();
import {
  getAllProduct,
  getProduct,
  addProduct,
  updateProduct,
  deleteProduct,
} from "../controllers/ProductController.js";

router.get("/getAllProducts", getAllProduct);

router.post("/addProduct/:id", addProduct);

router.put("/updateProduct/:id/admin/:adminId", updateProduct);

router.get("/getProduct/:id", getProduct);

router.delete("/deleteProduct/:id/admin/:adminId", deleteProduct);

export default router;
