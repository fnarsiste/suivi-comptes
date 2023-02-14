/*
* EXTENSION DE LA CLASSE "ARRAY"
*/

//Méthode "isArray"
if(!Array.isArray) {
  Array.isArray = function(arg) {
    return Object.prototype.toString.call(arg) === '[object Array]';
  };
}

//Méthode "includes"
if (!Array.prototype.includes) {
  Object.defineProperty(Array.prototype, 'includes', {
    value: function(searchElement, fromIndex) {

      if (this == null) {
        throw new TypeError('"this" est nul ou non défini');
      }

      // 1. Soit o égal à ? Object(cette valeur).
      var o = Object(this);

      // 2. Soit len égal à ? Length(? Get(o, "length")).
      var len = o.length >>> 0;

      // 3. Si len = 0, renvoyer "false".
      if (len === 0) {
        return false;
      }

      // 4. Soit n = ? ToInteger(fromIndex).
      // Pour la cohérence du code, on gardera le nom anglais "fromIndex" pour la variable auparavant appelée "indiceDépart"
      //    (Si fromIndex n'est pas défini, cette étape produit la valeur 0.)
      var n = fromIndex | 0;

      // 5. Si n ≥ 0,
      //  a. Alors k = n.
      // 6. Sinon, si n < 0,
      //  a. Alors k = len + n.
      //  b. Si k < 0, alors k = 0.
      var k = Math.max(n >= 0 ? n : len - Math.abs(n), 0);

      function sameValueZero(x, y) {
        return x === y || (typeof x === 'number' && typeof y === 'number' && isNaN(x) && isNaN(y));
      }

      // 7. Répéter tant que k < len
      while (k < len) {
        // a. Soit elementK le résultat de ? Get(O, ! ToString(k)).
        // b. Si SameValueZero(searchElement, elementK) est vrai, renvoyer "true".
        if (sameValueZero(o[k], searchElement)) {
          return true;
        }
        // c. Augmenter la valeur de k de 1.
        k++;
      }

      // 8. Renvoyer "false"
      return false;
    }
  });
}

//Méthode "forEach"
if (!Array.prototype.forEach) {
    Array.prototype.forEach = function(callback /*, thisArg*/) {

    var T, k;

    if (this == null) {
      throw new TypeError(' this vaut null ou n est pas défini');
    }

    // 1. Soit O le résultat de l'appel à ToObject
    //    auquel on a passé |this| en argument.
    var O = Object(this);

    // 2. Soit lenValue le résultat de l'appel de la méthode
    //    interne Get sur O avec l'argument "length".
    // 3. Soit len la valeur ToUint32(lenValue).
    var len = O.length >>> 0;

    // 4. Si IsCallable(callback) est false, on lève une TypeError.
    // Voir : http://es5.github.com/#x9.11
    if (typeof callback !== "function") {
      throw new TypeError(callback + ' n est pas une fonction');
    }

    // 5. Si thisArg a été fourni, soit T ce thisArg ;
    //    sinon soit T égal à undefined.
    if (arguments.length > 1) {
      T = arguments[1];
    }

    // 6. Soit k égal à 0
    k = 0;

    // 7. On répète tant que k < len
    while (k < len) {

      var kValue;

      // a. Soit Pk égal ToString(k).
      //   (implicite pour l'opérande gauche de in)
      // b. Soit kPresent le résultat de l'appel de la
      //    méthode interne HasProperty de O avec l'argument Pk.
      //    Cette étape peut être combinée avec c
      // c. Si kPresent vaut true, alors
      if (k in O) {

        // i. Soit kValue le résultat de l'appel de la
        //    méthode interne Get de O avec l'argument Pk.
        kValue = O[k];

        // ii. On appelle la méthode interne Call de callback
        //     avec T comme valeur this et la liste des arguments
        //     qui contient kValue, k, et O.
        callback.call(T, kValue, k, O);
      }
      // d. On augmente k de 1.
      k++;
    }
    // 8. on renvoie undefined
  };
}


/*
* EXTENSION DE LA CLASSE "STRING"
*/
if ( !String.prototype.includes ) {
    Object.defineProperty(String.prototype, "includes", {
        value : function(search, start) {
            if (typeof start !== 'number') {
                start = 0;
            }

            if (start + search.length > this.length) {
                return false;
            } else {
                return this.indexOf(search,start) !== -1;
            }
        }
    });
}

if (!String.prototype.startsWith) {
    Object.defineProperty(String.prototype, 'startsWith', {
        value: function(search, pos) {
            pos = !pos || pos < 0 ? 0 : +pos;
            return this.substring(pos, pos + search.length) === search;
        }
    });
}


/*
* EXTENSION DE LA CLASSE "MATH"
*/

(function(){

  /**
   * Fonction pour arrondir un nombre.
   *
   * @param {String}  type  Le type d'arrondi.
   * @param {Number}  value Le nombre à arrondir.
   * @param {Integer} exp   L'exposant (le logarithme en base 10 de la base pour l'arrondi).
   * @returns {Number}      La valeur arrondie.
   */
  function decimalAdjust(type, value, exp) {
    // Si l'exposant vaut undefined ou zero...
    if (typeof exp === 'undefined' || +exp === 0) {
      return Math[type](value);
    }
    value = +value;
    exp = +exp;
    // Si value n'est pas un nombre
        // ou si l'exposant n'est pas entier
    if (isNaN(value) || !(typeof exp === 'number' && exp % 1 === 0)) {
      return NaN;
    }
    // Décalage
    value = value.toString().split('e');
    value = Math[type](+(value[0] + 'e' + (value[1] ? (+value[1] - exp) : -exp)));
    // Re "calage"
    value = value.toString().split('e');
    return +(value[0] + 'e' + (value[1] ? (+value[1] + exp) : exp));
  }

  // Arrondi décimal
  if (!Math.round10) {
    Math.round10 = function(value, exp) {
      return decimalAdjust('round', value, exp);
    };
  }
  // Arrondi décimal inférieur
  if (!Math.floor10) {
    Math.floor10 = function(value, exp) {
      return decimalAdjust('floor', value, exp);
    };
  }
  // Arrondi décimal supérieur
  if (!Math.ceil10) {
    Math.ceil10 = function(value, exp) {
      return decimalAdjust('ceil', value, exp);
    };
  }
})();


/*
* AUTRES FONCTIONS
*/

// Returns if a value is typeName
function is(value, typeName) {
  return (typeof value === typeName);
}

// Returns if a value is a string
function isString(value) {
  return is(value, 'string') || value instanceof String;
}

// Returns if a value is really a number
function isNumber(value) {
  return is(value, 'number') && isFinite(value);
}

// Returns if a value is an array
function isArray(value) {
  return value && is(value, 'object') && value.constructor === Array;
  return Array.isArray(value);
}

// Returns if a value is a function
function isFunction(value) {
  return is(value, 'function');
}

// Returns if a value is an object
function isObject(value) {
  return value && is(value, 'object') && value.constructor === Object;
}

// Returns if a value is null
function isNull(value) {
  return value === null;
}

// Returns if a value is undefined
function isUndefined(value) {
  return is(value, 'undefined');
}

// Returns if a value is a boolean
function isBoolean(value) {
  return is(value, 'boolean');
}

// Returns if a value is a regexp
function isRegExp(value) {
  return value && is(value, 'object') && value.constructor === RegExp;
}

// Returns if value is an error object
function isError(value) {
  return value instanceof Error && !is(value.message, 'undefined');
}

// Returns if value is a date object
function isDate(value) {
  return value instanceof Date;
}

// Returns if a Symbol
function isSymbol(value) {
  return is(value, 'symbol');
}

function strToDateDotSeparator(str) {
    let pattern = /(\d{2})\.(\d{2})\.(\d{4})/;
    return new Date(str.replace(pattern,'$3-$2-$1'));
}

function strToDateSlashSeparator(str) {
    let pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
    return new Date(str.replace(pattern,'$3-$2-$1'));
}