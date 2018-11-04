//
//  Network.swift
//  MagicHacks
//
//  Created by Marc Davis on 11/3/18.
//  Copyright © 2018 Marc Davis. All rights reserved.
//

import Foundation

class GameNetwork {
    var session: URLSession
    
    init() {
        session = URLSession.shared
    }
    
    
    func sendTest() {
        let url = URL(string: "hello world")!
        let task = session.dataTask(with: url) { data, response, error in
            if let error = error {
                print(error)
                return
            }
            guard let HTTP = response as? HTTPURLResponse else {
                print("wat")
                return
            }
            print("succcccceess!")
            print(String(data: data!, encoding: .utf8)!)
        }
        task.resume()
    }
}
