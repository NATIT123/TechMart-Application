require("../models/UserDetail");
require("dotenv").config();
const mongoose = require("mongoose");
const bcrypt = require("bcrypt");
const User = mongoose.model("UserInfo");
const saltRounds = parseInt(process.env.saltRounds) || 10;

const registerUser = async (req, res) => {
  const { fullName, address, email, phone, password, image, role } = req.body;

  const oldUser = await User.findOne({ email: email });
  let encryptPassword;
  if (oldUser != null) {
    return res.send({ status: "notok", data: "User already registered" });
  } else {
    await bcrypt
      .genSalt(saltRounds)
      .then((salt) => {
        return bcrypt.hash(password, salt);
      })
      .then((hash) => {
        encryptPassword = hash;
      })
      .catch((err) => console.error(err.message));

    try {
      const userObject = {
        fullName: fullName,
        email: email,
        phone: phone,
        password: encryptPassword,
        image,
        address,
        role,
      };
      const savedUser = await User.collection.insertOne(userObject);
      console.log(savedUser.insertedId.toString());
      res.send({
        status: "ok",
        data: `User created successfully:${savedUser.insertedId.toString()}`,
      });
    } catch (err) {
      res.send({ status: "error", data: err.toString() });
    }
  }
};

const resgisterUserByGoogle = async (req, res) => {
  const { fullName, email, phone, password, image } = req.body;
  let encryptPassword;
  try {
    await bcrypt
      .genSalt(saltRounds)
      .then((salt) => {
        return bcrypt.hash(password, salt);
      })
      .then((hash) => {
        encryptPassword = hash;
      })
      .catch((err) => console.error(err.message));

    const userObject = {
      fullName: fullName,
      email: email,
      phone: phone,
      password: encryptPassword,
      image,
      address,
    };
    const savedUser = await User.collection.insertOne(userObject);
    console.log(savedUser.insertedId.toString());
    res.send({
      status: "ok",
      data: `User created successfully:${savedUser.insertedId.toString()}`,
    });
  } catch (err) {
    res.send({ status: "error", data: err.toString() });
  }
};

const loginUser = async (req, res) => {
  const { email, password } = req.body;

  try {
    const oldUser = await User.findOne({ email: email });

    if (!oldUser) {
      return res.send({
        status: "notok",
        data: "Email or password is not found",
      });
    }

    const match = await bcrypt.compare(password, oldUser.password);
    if (match) {
      res.send({
        status: "ok",
        data: `Login Succesfully:${oldUser._id.toString()}:${oldUser.fullName.toString()}:${oldUser.image.toString()}`,
      });
    } else {
      return res.send({
        status: "notok",
        data: "Email or password is not found",
      });
    }
  } catch (err) {
    res.send({ status: "error", data: err.message });
  }
};

const phoneIsExist = async (req, res) => {
  try {
    const { phone } = req.query;
    const user = await User.findOne({ phone: phone });

    if (!user) {
      return res.send({
        status: "notok",
        data: "Phone is not exist",
      });
    } else {
      return res.send({
        status: "ok",
        data: `Phone is exist:${user._id.toString()}`,
      });
    }
  } catch (err) {
    res.send({ status: "error", data: err.message });
  }
};

const changePassword = async (req, res) => {
  const { id } = req.params;
  const { password } = req.query;
  let encryptPassword;
  try {
    const ObjectId = mongoose.Types.ObjectId.createFromHexString(id);
    const user = await User.findOne({
      _id: ObjectId,
    });
    if (!user) {
      return res.send({
        status: "notok",
        data: "User is not exist",
      });
    }

    await bcrypt
      .genSalt(saltRounds)
      .then((salt) => {
        return bcrypt.hash(password, salt);
      })
      .then((hash) => {
        encryptPassword = hash;
      })
      .catch((err) => console.error(err.message));

    await User.updateOne(
      { _id: ObjectId },
      {
        $set: {
          encryptPassword,
        },
      }
    );
    res.send({ status: "ok", data: "Change Password Successfully" });
  } catch (err) {
    res.send({ status: "error", data: err.message });
  }
};

const detailUser = async (req, res) => {
  const { email } = req.query;
  const user = await User.findOne({ email: email });
  try {
    if (!user) {
      return res.send({
        status: "notok",
        message: "User is not exist",
      });
    }

    const userRespone = {
      id: user._id.toString(),
      email: user.email,
      phone: user.phone,
      password: user.password,
      image: user.image,
      address: user.address,
    };

    res.send({ status: "ok", data: userRespone });
  } catch (err) {
    res.send({ status: "error", message: err.message });
  }
};

module.exports = {
  registerUser,
  loginUser,
  phoneIsExist,
  changePassword,
  detailUser,
  resgisterUserByGoogle,
};
