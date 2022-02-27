import axios from "axios";

export const urlBackend="http://localhost:8087/";

export default axios.create({
  baseURL: urlBackend,
  headers: {
    "Content-type": "application/json"
  }
});