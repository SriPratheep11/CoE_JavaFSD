const ProductCard = ({ product }) => {
    const addToCart = () => {
      let cart = JSON.parse(localStorage.getItem('cart')) || [];
      cart.push(product);
      localStorage.setItem('cart', JSON.stringify(cart));
      alert(`${product.name} added to cart!`);
    };
  
    return (
      <div className="product-card">
        <img src={product.image} alt={product.name} width="200px" height="200px"/>
        <h3>{product.name}</h3>
        <p>${product.price}</p>
        <button onClick={addToCart}>Add to Cart</button>
      </div>
    );
  };
  
  export default ProductCard;
  