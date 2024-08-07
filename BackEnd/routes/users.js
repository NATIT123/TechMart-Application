const express = require("express");
const router = express.Router();
const {
  registerUser,
  loginUser,
  phoneIsExist,
  changePassword,
} = require("../controllers/UserController");

router.post("/register", registerUser);

router.post("/login", loginUser);

router.get("/phoneExist", phoneIsExist);

router.get("/changePassword/:id", changePassword);

module.exports = router;
