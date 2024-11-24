import { Router } from "express";
const router = Router();
import {
  getAllCategory,
  getCategory,
  addCategory,
  updateCategory,
  deleteCategory,
} from "../controllers/CategoryController.js";

router.get("/getAllCategory", getAllCategory);

router.post("/addCategory/:id", addCategory);

router.put("/updateCategory/:id/admin/:adminId", updateCategory);

router.get("/getCategory/:id", getCategory);

router.delete("/deleteCategory/:id/admin/:adminId", deleteCategory);

export default router;
