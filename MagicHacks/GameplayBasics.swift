//
//  File.swift
//  MagicHacks
//
//  Created by Marc Davis on 11/3/18.
//  Copyright © 2018 Marc Davis. All rights reserved.
//

import Foundation
import UIKit

enum CreatureType {
    case fire
    case water
    case lightning
    case earth
    case air
}

class Creature {
    let types: [CreatureType]
    
    init(types: [CreatureType]) {
        guard types.count == 1 || types.count == 2 else {
            print("creatures must have one or two types")
            exit(0)
        }
        self.types = types
    }
    
    func image() -> UIImage {
        print("not yet implementeded")
        return UIImage(imageLiteralResourceName: "air")
    }
}
