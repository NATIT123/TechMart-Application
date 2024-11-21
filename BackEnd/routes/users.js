import { Router } from "express";
const router = Router();
import {
  getAllUser,
  registerUser,
  loginUser,
  phoneIsExist,
  changePassword,
  detailUser,
  resgisterUserByGoogle,
  updateUser,
  addUser,
  deleteUser,
} from "../controllers/UserController.js";

router.get("/getAllUsers", getAllUser);

router.post("/addUser/:adminId", addUser);

router.post("/register", registerUser);

router.post("/login", loginUser);

router.get("/phoneExist", phoneIsExist);

router.get("/changePassword/:id", changePassword);

router.get("/detailUser", detailUser);

router.post("/registerByGoogle", resgisterUserByGoogle);

router.patch("/updateUser/:id", updateUser);

router.delete("/deleteUser/:id/admin/:adminId", deleteUser);

export default router;
