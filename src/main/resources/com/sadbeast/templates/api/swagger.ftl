{
  "swagger": "2.0",
  "info": {
    "description": "An API for a website that does nothing.",
    "version": "1.0.0",
    "title": "SadBeast API",
    "contact": {
      "email": "kentsmith@gmail.com"
    },
    "license": {
      "name": "Apache 2.0",
      "url": "http://www.apache.org/licenses/LICENSE-2.0.html"
    }
  },
  "host": "${host}",
  "basePath": "/api",
  "tags": [
    {
      "name": "post",
      "description": "Everything about posts"
    }
  ],
  "schemes": [
    "${scheme}"
  ],
  "paths": {
    "/post": {
      "get": {
        "tags": [
          "post"
        ],
        "summary": "Get a post",
        "description": "Currently only a random post is supported",
        "operationId": "randomPost",
        "produces": [
          "application/json"
        ],
        "parameters": [
          {
            "in": "query",
            "name": "random",
            "description": "If this parameter exists, you will get a random post",
            "required": true,
            "type": "string",
            "enum": [
              "random"
            ]
          }
        ],
        "responses": {
          "200": {
            "description": "successful operation",
            "schema": {
              "$ref": "#/definitions/Post"
            }
          }
        }
      }
    },
    "/posts": {
      "get": {
        "tags": [
          "post"
        ],
        "summary": "Get posts",
        "description": "Gets the latest 20 posts",
        "operationId": "getPosts",
        "produces": [
          "application/json"
        ],
        "parameters": [
          {
            "in": "query",
            "name": "prev",
            "description": "Get the previous 20 posts before the index specified in prev",
            "required": false,
            "type": "string"
          },
          {
            "in": "query",
            "name": "q",
            "description": "Search for posts containing the query in q, limited to 50 results",
            "required": false,
            "type": "string"
          }
        ],
        "responses": {
          "200": {
            "description": "successful operation",
            "schema": {
              "type": "array",
              "items": {
                "$ref": "#/definitions/Post"
              }
            }
          }
        }
      }
    }
  },
  "definitions": {
    "Post": {
      "required": [
        "content"
      ],
      "properties": {
        "content": {
          "type": "string",
          "example": "This is a post"
        },
        "created": {
          "type": "integer",
          "format": "date-time",
          "example": "2015-01-23T15:29:25.004+0000"
        }
      },
      "xml": {
        "name": "Post"
      }
    }
  }
}
