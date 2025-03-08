import axios from "axios";


// Gets all post in the DB
export const getPosts = async ()=> {
    try{
        const response =  await axios.get("http://localhost:8080/api/wecode/posts");
        return response.data;
    } catch (error){
        console.log(error);
        throw error;
    }
}


// Get post data by user ID
export const getPostById = async (id)=> {
    try{
        const response = await axios.get(`http://localhost:8080/api/wecode/posts/${id}`);
        return response.data;
    } catch (error){
        console.log(error);
        throw error
    }
}


// Gets a all user post by user ID
export const getPostByUserId = async (userId)=> {
    try{
        const response = await axios.get(`http://localhost:8080/api/wecode/posts/user/${userId}`);
        return response.data;
    } catch (error){
        console.log(error)
    }
}