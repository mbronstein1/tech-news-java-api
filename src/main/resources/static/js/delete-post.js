const deletePostBtn = document.querySelector('.delete-post-btn');

const deleteFormHandler = async (e) => {
    e.preventDefault();

    const splitUrl = window.location.toString().split('/');
    const id = splitUrl[splitUrl.length - 1];

    const response = await fetch(`/api/posts/${id}`, {
        method: 'DELETE'
    });

    if (response.ok) {
        document.location.replace('/dashboard/');
    } else {
        alert(response.statusText);
    }
}

deletePostBtn.addEventListener('click', deleteFormHandler)