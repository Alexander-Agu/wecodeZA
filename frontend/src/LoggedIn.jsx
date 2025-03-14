import React from 'react'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import Feed from "./components/feed/Feed"
import PostHome from './components/postHome/PostHome';
import ViewProfile from './components/viewProfile/ViewProfile';
import Profile from './components/profile/Profile';
import Write from './components/write/Write';

const router = createBrowserRouter([
    {
        path: "/feed",
        element: <Feed />,
        // errorElement: <h2>404 Page not found!</h2>
    },
    {
        path: "/postHome/:postId",
        element: <PostHome />
    },
    {
        path: "/viewAuthor/:authorId",
        element: <ViewProfile />
    },
    {
        path: "/viewProfile",
        element: <Profile />
    },
    {
        path: "/write",
        element: <Write />
    },
    {
        path: "/editPost/:postId",
        element: <Write />
    }

]);

function LoggedIn() {
  return (
    <RouterProvider router={router} />
  )
}

export default LoggedIn