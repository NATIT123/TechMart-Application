import { Router } from "express";
const router = Router();
import {
  registerUser,
  loginUser,
  phoneIsExist,
  changePassword,
  detailUser,
  resgisterUserByGoogle,
  updateUser,
} from "../controllers/UserController.js";

router.post("/register", registerUser);

router.post("/login", loginUser);

router.get("/phoneExist", phoneIsExist);

router.get("/changePassword/:id", changePassword);

router.get("/detailUser", detailUser);

router.post("/registerByGoogle", resgisterUserByGoogle);

router.put("/updateUser/:id", updateUser);

export default router;
