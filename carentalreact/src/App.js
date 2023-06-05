import logo from './logo.svg';
import './App.css';
// Import everything needed to use the `useQuery` hook
import { useQuery, gql } from '@apollo/client';

const GET_LOCATIONS = gql`
  query allRentalAgreements {
      rentalAgreements {
          id
          customer {
              name
              id
          }
          cars {
              brand
              price
          }
      }

  }
`;

function DisplayLocations() {
  const { loading, error, data } = useQuery(GET_LOCATIONS);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error : {error.message}</p>;

  return data.rentalAgreements.map(({ id, customer, cars }) => (
    <div key={id}>
      <h3>{customer.name}</h3>
      <br />
      <b>About this location:</b>
      {cars.map((datas) => (
        <li>{datas.brand} => {datas.price}</li>

                ))}
      <br />
    </div>
  ));

/*  return data.locations.map(({ id, name, description, photo }) => (
    <div key={id}>
      <h3>{name}</h3>
      <img width="400" height="250" alt="location-reference" src={`${photo}`} />
      <br />
      <b>About this location:</b>
      <p>{description}</p>
      <br />
    </div>
  ));*/
}

export default function App() {
  return (
    <div>
      <h2>My first Apollo app 🚀</h2>
      <br/>
      <DisplayLocations />
    </div>
  );
}

/*function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;*/
