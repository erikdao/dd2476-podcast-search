import { useHistory } from "react-router-dom";

const SearchResultItem = ({ episode }) =>  {
    let history = useHistory();
    const handleClick = () => {
        // history.pushState({title, description}, '/episode');
        // console.log(history);
        history.push('episode', episode);
    };

    return (
        <div className="search-result-item" onClick={handleClick}>
            <h3>{episode.show.showName}</h3>
            <h2>{episode.episodeName}</h2>
        </div>
    )
}

export default SearchResultItem;