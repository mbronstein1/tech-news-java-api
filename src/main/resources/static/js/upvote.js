const upvoteBtn = document.querySelector('.upvote-btn');

const upvoteClickHandler = async (e) => {
    e.preventDefault();

    const splitUrl = window.location.toString().split('/');
    const id = splitUrl[splitUrl.length - 1];

    const response = await fetch('/posts/upvote', {
        method: 'PUT',
        body: JSON.stringify({
            postId: id
            }),
        headers: {
            'Content-Type: application/json'
           }
        });

    if (response.ok) {
            document.location.reload;
    } else {
            alert(response.statusText);
    }
};

upvoteBtn.addEventListener('click', upvoteClickHandler);