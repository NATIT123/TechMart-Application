const express = require("express");
const router = express.Router();
const {
  registerUser,
  loginUser,
  phoneIsExist,
  changePassword,
  detailUser,
  resgisterUserByGoogle,
  updateUser,
} = require("../controllers/UserController");

router.post("/register", registerUser);

router.post("/login", loginUser);

router.get("/phoneExist", phoneIsExist);

router.get("/changePassword/:id", changePassword);

router.get("/detailUser", detailUser);

router.post("/registerByGoogle", resgisterUserByGoogle);

router.put("/updateUser/:id", updateUser);

module.exports = router;
